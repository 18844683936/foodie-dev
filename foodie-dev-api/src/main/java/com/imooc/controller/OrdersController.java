package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.service.AddressService;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description: 订单Controller
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-14
 */
@Api(value = "订单相关",tags = {"订单相关接口"})
@RestController
@RequestMapping("orders")
@Log4j
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response){


        log.info("用户下单"+ JsonUtils.objectToJson(submitOrderBO));

        if (submitOrderBO.getPayMethod() != PayMethod.ALIPPAY.type && submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type){
            return IMOOCJSONResult.errorMsg("支付方式不支持");
        }

//        1. 创建订单
        String orderId = orderService.createOrder(submitOrderBO);
//        2. 创建订单以后，移除购物车中 已结算（已提交）的商品
        //TODO 整合redis后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);
//        3. 向支付中心发送当前订单，用于保存支付中心的订单数据

        return IMOOCJSONResult.ok(orderId);
    }


}