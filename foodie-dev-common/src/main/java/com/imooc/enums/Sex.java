package com.imooc.enums;

/**
 * @Description  性别枚举
 * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @since v1.0
 * @Date  2022/6/29 9:03
 * @Param
 * @return
 **/
public enum Sex {
    women(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final  Integer type;
    public final  String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
