package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouseService;
import com.imooc.service.CategoryService;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description: 首页展示的相关接口
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-27
 */
@RestController
@RequestMapping("index")
@Api(value = "首页",tags = "首页展示的相关接口")
public class IndexController {
    @Autowired
    private CarouseService carouseService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/carousel")
    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    public IMOOCJSONResult carousel(){

        List<Carousel> result = carouseService.queryAll(YesOrNo.YES.type);
        //3 请求成功，用户名没有重复
        return IMOOCJSONResult.ok(result);
    }

    @GetMapping("/cats")
    @ApiOperation(value = "获取商品分类（一级分类）",notes = "获取商品分类（一级分类）",httpMethod = "GET")
    public IMOOCJSONResult cats(){
        List<Category> result = categoryService.queryAllRootLevelCat();
        //3 请求成功，用户名没有重复
        return IMOOCJSONResult.ok(result);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){

        if (rootCatId == null){
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> result = categoryService.getSubCatList(rootCatId);
        //3 请求成功，用户名没有重复
        return IMOOCJSONResult.ok(result);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据",notes = "查询每个一级分类下的最新6条商品数据",httpMethod = "GET")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId){

        if (rootCatId == null){
            return IMOOCJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> result = categoryService.getSixNewItemsLazy(rootCatId);
        //3 请求成功，用户名没有重复
        return IMOOCJSONResult.ok(result);
    }
}