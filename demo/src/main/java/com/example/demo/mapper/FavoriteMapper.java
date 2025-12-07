package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FavoriteMapper {
    // 添加收藏
    @Insert("INSERT INTO favorites(user_id, goods_id) VALUES(#{userId}, #{goodsId})")
    void addFavorite(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    // 取消收藏
    @Delete("DELETE FROM favorites WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    void removeFavorite(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    // 查询用户收藏了哪些商品ID
    @Select("SELECT goods_id FROM favorites WHERE user_id = #{userId}")
    List<Integer> selectFavoriteGoodsIds(Integer userId);
}