package com.example.demo.mapper;

import com.example.demo.model.Cart;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CartMapper {
    // ... 原有的方法 (addToCart, selectByUserId, selectExist, updateCount) 保持不变 ...
    @Insert("INSERT INTO cart(user_id, goods_id, count) VALUES(#{userId}, #{goodsId}, #{count})")
    void addToCart(Cart cart);

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    List<Cart> selectByUserId(Integer userId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    Cart selectExist(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    @Update("UPDATE cart SET count = count + #{count} WHERE id = #{id}")
    void updateCount(Cart cart);

    // [新增] 删除单条记录
    @Delete("DELETE FROM cart WHERE id = #{id}")
    void deleteById(Integer id);
}