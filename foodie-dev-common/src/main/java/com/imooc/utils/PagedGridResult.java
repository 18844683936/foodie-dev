package com.imooc.utils;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-11
 */
@Data
public class PagedGridResult {
    private int page; // 当前页数
    private int total; //总页数
    private long records; //总记录数
    private List<?> rows; //每页显示的内容
}