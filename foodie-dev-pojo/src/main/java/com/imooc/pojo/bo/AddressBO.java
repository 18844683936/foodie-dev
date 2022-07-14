package com.imooc.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 地址
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String  detail;
}