package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);


    /*
     * @Description  用户修改地址
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/14 20:27
     * @Param [addressBO]
     * @return void
     **/
    void updateUserAddress(AddressBO addressBO);

    /*
     * @Description  删除地址
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/14 21:03
     * @Param [userId, addressId]
     * @return void
     **/
    void delUserAddress(String userId,String addressId);

    /*
     * @Description  修改为默认地址
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/14 21:03
     * @Param [userId, addressId]
     * @return void
     **/
    void updateUserAddressToBeDefault(String userId,String addressId);
}
