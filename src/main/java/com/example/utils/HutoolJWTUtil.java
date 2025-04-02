package com.example.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.example.pojo.domain.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Hutool JWT 工具类
 * 该类提供生成和解析 JWT 的方法。
 */
public class HutoolJWTUtil {

    /**
     * 生成token
     * @param sysUser 用户信息对象，包含用户名和用户ID
     * @return 返回生成的JWT token
     */
    public static String createToken(SysUser sysUser){
        DateTime now = DateTime.now(); // 获取当前时间
        DateTime newTime = now.offsetNew(DateField.MINUTE, 120); // 设置token过期时间为120分钟后
        Map<String,Object> payload = new HashMap<String,Object>(); // 创建载荷Map
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 载荷中添加用户名和用户ID
        payload.put("username", sysUser.getUsername());
        payload.put("aid", sysUser.getId());
        String key = "www.yzm.cn"; // 定义密钥
        String token = JWTUtil.createToken(payload, key.getBytes()); // 生成token
        return token; // 返回生成的token
    }

    /**
     * 解析token
     * @param token JWT token字符串
     * @return 返回token中包含的用户ID
     */
    public static Long parseToken(String token){
        final JWT jwt = JWTUtil.parseToken(token); // 解析token
        return Long.parseLong(jwt.getPayload("aid").toString()); // 获取并返回用户ID
    }
}
