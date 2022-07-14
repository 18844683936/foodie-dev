package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);

}
