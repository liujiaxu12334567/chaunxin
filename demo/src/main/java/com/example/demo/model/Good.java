package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Good {
    private Integer id;
    private String name;
    private Integer favor;
    private Integer count;
    private BigDecimal price;
    private Boolean discount;
    private String introduce;
    private String imgurl;
    private List<Comment> comments;
    private List<GoodWall> goods_wall;
    private List<Tag> tags;
}
