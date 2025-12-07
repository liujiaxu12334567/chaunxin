package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 登录：根据用户名查询用户
    @Select("SELECT * FROM users WHERE name = #{name}")
    User selectByName(String name);

    // 注册：插入新用户
    @Insert("INSERT INTO users(name, password) VALUES(#{name}, #{password})")
    void insert(User user);
}