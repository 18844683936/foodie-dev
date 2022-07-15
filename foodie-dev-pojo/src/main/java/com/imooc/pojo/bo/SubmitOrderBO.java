package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 用于创建订单的BO
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOrderBO {

    private String userId;

    private String itemSpecIds;

    private String addressId;

    private Integer payMethod;

    private String leftMsg;
}