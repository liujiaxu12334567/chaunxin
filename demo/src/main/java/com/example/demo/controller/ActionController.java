package com.example.demo.controller;

import com.example.demo.mapper.CartMapper;
import com.example.demo.mapper.FavoriteMapper;
import com.example.demo.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/action")
@CrossOrigin(origins = "*")
public class ActionController {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;

    @PostMapping("/cart/add")
    public Map<String, Object> addToCart(@RequestBody Cart cart) {
        Cart exist = cartMapper.selectExist(cart.getUserId(), cart.getGoodsId());
        if (exist != null) {
            exist.setCount(cart.getCount());
            cartMapper.updateCount(exist);
        } else {
            cartMapper.addToCart(cart);
        }
        return Map.of("success", true, "message", "已加入购物车");
    }

    @GetMapping("/cart/list")
    public List<Cart> getCart(@RequestParam Integer userId) {
        return cartMapper.selectByUserId(userId);
    }

    @DeleteMapping("/cart/remove")
    public Map<String, Object> removeFromCart(@RequestParam Integer id) {
        cartMapper.deleteById(id);
        return Map.of("success", true, "message", "已移除");
    }
    @PostMapping("/favorite/toggle")
    public Map<String, Object> toggleFavorite(@RequestBody Map<String, Integer> params) {
        Integer userId = params.get("userId");
        Integer goodsId = params.get("goodsId");
        Boolean isFavorite = params.get("isFavorite") == 1; // 1:收藏, 0:取消

        if (isFavorite) {
            favoriteMapper.addFavorite(userId, goodsId);
        } else {
            favoriteMapper.removeFavorite(userId, goodsId);
        }
        return Map.of("success", true);
    }

    @GetMapping("/favorite/list")
    public List<Integer> getFavoriteIds(@RequestParam Integer userId) {
        return favoriteMapper.selectFavoriteGoodsIds(userId);
    }
}