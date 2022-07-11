package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 二级分类VO
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-05
 */
@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;


    //三级分类vo list
    private List<SubCategoryVO> subCatList;

}