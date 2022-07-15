package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.bo.UserBO;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-27
 */
@Service
public interface OrderService {

    /*
     * @Description  创建订单相关信息
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/15 9:26
     * @Param [submitOrderBO]
     * @return void
     **/
    void createOrder(SubmitOrderBO submitOrderBO);


}