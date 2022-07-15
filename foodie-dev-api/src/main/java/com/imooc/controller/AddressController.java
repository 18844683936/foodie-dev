package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 地址
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-14
 */
@Api(value = "地址相关",tags = {"地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    /**
     * 用户在确认订单页面，可以针对收获地址做如下操作:
     * 1. 查询用户的所有收获地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4，修改收货地址
     * 5. 设置默认地址
     */

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    @ApiOperation(value = "根据用户id查询收货地址列表",notes = "根据用户id查询收货地址列表",httpMethod = "POST")
    public IMOOCJSONResult list(@RequestParam String userId){

        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.ok();
        }

        List<UserAddress> list = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(list);
    }

    @PostMapping("/add")
    @ApiOperation(value = "用户新增地址",notes = "用户新增地址",httpMethod = "POST")
    public IMOOCJSONResult add(@RequestBody AddressBO addressBO){

        IMOOCJSONResult checkAddress = checkAddress(addressBO);

        if (checkAddress.getStatus() != 200){
            return checkAddress;
        }

        addressService.addNewUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户修改地址",notes = "用户修改地址",httpMethod = "POST")
    public IMOOCJSONResult update(@RequestBody AddressBO addressBO){

        if (StringUtils.isBlank(addressBO.getAddressId())){
            return IMOOCJSONResult.errorMsg("addressId不能为空");
        }

        IMOOCJSONResult checkAddress = checkAddress(addressBO);

        if (checkAddress.getStatus() != 200){
            return checkAddress;
        }

        addressService.updateUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }


    @PostMapping("/delete")
    @ApiOperation(value = "用户删除地址",notes = "用户删除地址",httpMethod = "POST")
    public IMOOCJSONResult delete(@RequestParam String userId, @RequestParam String addressId){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IMOOCJSONResult.errorMsg("");
        }
        addressService.delUserAddress(userId,addressId);
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/setDefalut")
    @ApiOperation(value = "用户设置默认地址",notes = "用户设置默认地址",httpMethod = "POST")
    public IMOOCJSONResult setDefalut(@RequestParam String userId, @RequestParam String addressId){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IMOOCJSONResult.errorMsg("");
        }
        addressService.updateUserAddressToBeDefault(userId,addressId);
        return IMOOCJSONResult.ok();
    }

    private IMOOCJSONResult checkAddress(AddressBO addressBO){
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }

        return  IMOOCJSONResult.ok();
    }
}