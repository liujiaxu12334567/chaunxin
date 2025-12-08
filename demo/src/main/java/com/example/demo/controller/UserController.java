package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 查询用户
        User dbUser = userMapper.selectByName(user.getName());

        // 2. 校验
        if (dbUser == null) {
            result.put("success", false);
            result.put("message", "用户不存在");
        } else if (!dbUser.getPassword().equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "密码错误");
        } else {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("user", dbUser);
        }
        return result;
    }

    // 注册接口
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 检查是否已存在
        User existUser = userMapper.selectByName(user.getName());
        if (existUser != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        // 2. 插入数据库
        try {
            userMapper.insert(user);
            result.put("success", true);
            result.put("message", "注册成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "注册失败: " + e.getMessage());
        }
        return result;
    }
}