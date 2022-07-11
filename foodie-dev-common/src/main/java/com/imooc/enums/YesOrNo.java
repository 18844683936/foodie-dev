package com.imooc.enums;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-04
 */
public enum YesOrNo {

    NO(0,"否"),
    YES(1,"是");

    public final  Integer type;
    public final  String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}