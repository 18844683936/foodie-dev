package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-06
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Autowired
    private Sid sid;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        Integer postAmount = 0; //包邮费用为0


        UserAddress address = addressService.queryUserAddress(userId, addressId);
        String orderId = sid.nextShort();
        //1. 新订单数据保存
        Orders newOrder = Orders.builder()
                .id(orderId)
                .userId(userId)
                .receiverName(address.getReceiver())
                .receiverMobile(address.getMobile())
                .receiverAddress(address.getProvince() + " "
                                + address.getCity() + " "
                                + address.getDistrict() + " "
                                + address.getDetail())
//                .totalAmount()
//                .realPayAmount()
                .postAmount(postAmount)
                .payMethod(payMethod)
                .leftMsg(leftMsg)
                .isComment(YesOrNo.NO.type)
                .isDelete(YesOrNo.NO.type)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();

        //2. 循环根据itemSpecIds保存订单商品信息表
        Integer totalAmount = 0;//商品原件累计
        Integer realPayAmount = 0; //优惠后的实际支付价格累计
        for (String itemSpecId : itemSpecIds.split(",")) {
            //TODO 整合Redis后，商品购买的数量重新从Redis的购物车中获取
            int buyCounts = 1;
            //2.1 根据规格id获得价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemsSpec.getPriceDiscount() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            //2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            //2.3 循环保存子订单数据到数据库 订单商品表
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = OrderItems.builder()
                    .orderId(orderId)
                    .id(subOrderId)
                    .itemId(itemId)
                    .itemName(item.getItemName())
                    .itemImg(imgUrl)
                    .buyCounts(buyCounts)
                    .itemSpecId(itemSpecId)
                    .itemSpecName(itemsSpec.getName())
                    .price(itemsSpec.getPriceDiscount())
                    .build();

            orderItemsMapper.insert(subOrderItem);

            //2.4 在用户提交订单后，要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId,buyCounts);
        }

        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        //3. 保存订单状态表
        OrderStatus waitPayOrderStatus = OrderStatus.builder()
                .orderId(orderId)
                .orderStatus(OrderStatusEnum.WAIT_PAY.type)
                .createdTime(new Date())
                .build();

        orderStatusMapper.insert(waitPayOrderStatus);

    }

}