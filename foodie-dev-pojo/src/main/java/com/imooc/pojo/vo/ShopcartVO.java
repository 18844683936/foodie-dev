package com.imooc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 购物车
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopcartVO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}