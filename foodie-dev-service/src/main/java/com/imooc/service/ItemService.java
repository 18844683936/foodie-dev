package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @description: 商品Service
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-07-06
 */
public interface ItemService {

    /*
     * @Description  根据商品ID查询详情
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/6 15:29
     * @Param [itemId]
     * @return com.imooc.pojo.Items
     **/
    Items queryItemById(String itemId);

    /*
     * @Description  根据商品id查询商品图片列表
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/6 15:30
     * @Param [itemId]
     * @return java.util.List<com.imooc.pojo.ItemsImg>
     **/
    List<ItemsImg> queryItemImgList(String itemId);

    /*
     * @Description  根据商品id查询商品规格
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/6 15:30
     * @Param [itemId]
     * @return java.util.List<com.imooc.pojo.ItemsImg>
     **/
    List<ItemsSpec> queryItemSpecList(String itemId);

    /*
     * @Description  根据商品id查询商品参数
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/6 15:30
     * @Param [itemId]
     * @return java.util.List<com.imooc.pojo.ItemsImg>
     **/
    List<ItemsParam> queryItemParamList(String itemId);


    /*
     * @Description  根据商品id查询商品的评价登记数量
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/7 9:50
     * @Param [itemId]
     * @return com.imooc.pojo.vo.CommentLevelCountsVO
     **/
    CommentLevelCountsVO queryCommentCounts(String itemId);


    /*
     * @Description  根据id查询商品的评价（带有分类）
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/7 12:04
     * @Param [itemId, level]
     * @return java.util.List<com.imooc.pojo.vo.ItemCommentVO>
     **/
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /*
     * @Description  搜索商品列表
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/12 9:39
     * @Param [keywords, sort, page, pageSize]
     * @return com.imooc.utils.PagedGridResult
     **/
    PagedGridResult searchItems(String keywords,
                                String sort, Integer page, Integer pageSize);

    /*
     * @Description  搜索商品列表
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/7/12 9:39
     * @Param [keywords, sort, page, pageSize]
     * @return com.imooc.utils.PagedGridResult
     **/
    PagedGridResult searchItems(Integer catId,
                                String sort, Integer page, Integer pageSize);
}