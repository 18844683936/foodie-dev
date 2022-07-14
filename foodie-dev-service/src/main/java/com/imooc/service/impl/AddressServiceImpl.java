package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.service.CarouseService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-04
 */
@Service
public class AddressServiceImpl implements AddressService {
    
    @Autowired(required = false)
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryAll(String userId) {
        UserAddress condition = new UserAddress();
        condition.setUserId(userId);
        List<UserAddress> list = userAddressMapper.select(condition);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addNewUserAddress(AddressBO addressBO) {
        //1. 判断当前用户是否存在地址，如果没有，则新增为 ‘默认地址’
        List<UserAddress> addressList = queryAll(addressBO.getUserId());
        Integer isDefault  = 0;
        if(addressList == null || addressList.isEmpty()) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        //2. 保存到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setUpdatedTime(new Date());
        newAddress.setCreatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }
}