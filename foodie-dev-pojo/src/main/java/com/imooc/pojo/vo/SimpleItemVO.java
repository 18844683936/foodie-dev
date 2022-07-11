package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @description: 6个最新商品的简单数据类型
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-06
 */
@Data
public class SimpleItemVO {
    private String itemId;
    private String itemName;
    private String itemUrl;
    private String createdTime;

}