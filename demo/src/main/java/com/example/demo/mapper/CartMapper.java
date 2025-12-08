package com.example.demo.mapper;

import com.example.demo.model.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CartMapper {
    void addToCart(Cart cart);

    List<Cart> selectByUserId(Integer userId);

    Cart selectExist(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    void updateCount(Cart cart);

    void deleteById(Integer id);
}