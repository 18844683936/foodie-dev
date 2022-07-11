package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-27
 */
@Service
public interface UserService {
    /*
     * @Description  判断用户名是否存在
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/6/27 9:10
     * @Param []
     * @return void
     **/
    boolean QueryUsernameIsExit(String username);

    /*
     * @Description  创建用户
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/6/29 8:51
     * @Param [userBO]
     * @return com.imooc.pojo.Users
     **/
    Users createUser(UserBO userBO);
    /*
     * @Description  判断用户是否存在
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/6/30 9:45
     * @Param [username, password]
     * @return com.imooc.pojo.Users
     **/
    Users queryUserForLogin(String username,String password);
}