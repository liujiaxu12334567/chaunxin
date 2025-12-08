package com.example.demo.controller;

import com.example.demo.mapper.GoodsMapper;
import com.example.demo.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
@CrossOrigin(origins = "*")
public class Goodscontroller {

    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping
    public List<Good> getAllGoods() {
        return goodsMapper.selectGoods();
    }
    @GetMapping("/detail")
    public Good getGoodsDetail(Integer id) {
        return goodsMapper.selectGoods().stream().filter(good -> good.getId().equals(id)).findFirst().orElse(null);
    }
}