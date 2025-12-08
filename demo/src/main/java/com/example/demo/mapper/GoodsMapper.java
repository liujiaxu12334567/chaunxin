package com.example.demo.mapper;

import com.example.demo.model.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    List <Good> selectGoods();
    Good selectGoodsDetail(Integer id);
}
