package com.imooc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
*@description: 用于展示商品评价的VO
*@author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
*@create: 2022-07-07
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nikeName;
}