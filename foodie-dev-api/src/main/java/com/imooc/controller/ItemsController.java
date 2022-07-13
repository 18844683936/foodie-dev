package com.imooc.controller;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @description: 商品信息展示的相关接口
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-27
 */
@RestController
@RequestMapping("items")
@Api(value = "商品接口",tags = "商品信息展示的相关接口")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;


    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @PathVariable String itemId ){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        List<ItemsParam> itemParams = itemService.queryItemParamList(itemId);

        ItemInfoVO itemInfoVO = ItemInfoVO.builder()
                .item(item)
                .itemImgList(itemImgList)
                .itemParams(itemParams)
                .itemSpecList(itemSpecList).build();

        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId ){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return IMOOCJSONResult.ok(countsVO);
    }
    @GetMapping("/comments")
    @ApiOperation(value = "查询商品评价",notes = "查询商品评价",httpMethod = "GET")
    public IMOOCJSONResult comments(
                                    @ApiParam(name = "itemId",value = "商品id",required = true)
                                    @RequestParam String itemId,
                                    @ApiParam(name = "level",value = "商品等级",required = false)
                                    @RequestParam Integer level,
                                    @ApiParam(name = "page",value = "第几页",required = false)
                                    @RequestParam Integer page,
                                    @ApiParam(name = "pageSize",value = "每页条数",required = false)
                                    @RequestParam Integer pageSize){

        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        if (Objects.isNull(page)){
            page = 1;
        }

        if (Objects.isNull(pageSize)){
                pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return IMOOCJSONResult.ok(grid);
    }


    @GetMapping("/search")
    @ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
    public IMOOCJSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize){

        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg(null);
        }

        if (Objects.isNull(page)){
            page = 1;
        }

        if (Objects.isNull(pageSize)){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);

        return IMOOCJSONResult.ok(grid);
    }

    @GetMapping("/catItems")
    @ApiOperation(value = "通过分类id搜索商品列表",notes = "通过分类id搜索商品列表",httpMethod = "GET")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "每页条数",required = false)
            @RequestParam Integer pageSize){

        if (Objects.isNull(catId)){
            return IMOOCJSONResult.errorMsg("分类id不能为空");
        }

        if (Objects.isNull(page)){
            page = 1;
        }

        if (Objects.isNull(pageSize)){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);

        return IMOOCJSONResult.ok(grid);
    }

}