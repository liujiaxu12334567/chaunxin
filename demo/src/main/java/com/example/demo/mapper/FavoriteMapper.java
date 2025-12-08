package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FavoriteMapper {
    void addFavorite(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    void removeFavorite(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    List<Integer> selectFavoriteGoodsIds(Integer userId);
}