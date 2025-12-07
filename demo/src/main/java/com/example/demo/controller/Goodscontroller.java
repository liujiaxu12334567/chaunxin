package com.example.demo.controller;

import com.example.demo.mapper.GoodsMapper;
import com.example.demo.model.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin; // 1. 记得加这个导入
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
@CrossOrigin(origins = "*")  // 2. 加上这行注解，允许所有来源访问
public class Goodscontroller {

    @Autowired
    private GoodsMapper goodsMapper;

    @GetMapping
    public List<Good> getAllGoods() {
        return goodsMapper.selectGoods();
    }
}