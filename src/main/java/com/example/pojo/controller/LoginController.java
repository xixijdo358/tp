package com.example.pojo.controller;

import com.example.base.BaseResult;
import com.example.pojo.domain.SysUser;
import com.example.pojo.service.ISysUserService;
import com.example.utils.HutoolJWTUtil;
import com.example.utils.Md5Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**功能描述：系统后台登录前端控制器
 * 该控制器负责处理用户登录和退出的请求。
 */
@RestController
public class LoginController {

    private final ISysUserService sysUserService; // 用户服务接口

    public LoginController(ISysUserService sysUserService) {
        this.sysUserService = sysUserService; // 构造函数注入用户服务
    }

    @PostMapping("login")
    public BaseResult login(@RequestBody SysUser sysUser, HttpServletRequest request){
        SysUser dbSysUser = sysUserService.login(sysUser); // 从数据库中获取用户信息
        if(dbSysUser == null){
            return BaseResult.fail("登录失败，账号不存在"); // 用户不存在
        } else if (!dbSysUser.getPassword().equals(Md5Util.MD5(sysUser.getPassword()))) {
            return BaseResult.fail("登录失败，密码不正确"); // 密码不正确
        } else if (dbSysUser.getStatus() == 0) {
            return BaseResult.fail("登录失败，账号被封禁"); // 账号被封禁
        }
        // 生成token
        String token = HutoolJWTUtil.createToken(dbSysUser); // 创建JWT token
        request.getServletContext().setAttribute("token", token); // 将token存入上下文
        Map<String, Object> resultMap = new HashMap<>(); // 创建结果映射
        resultMap.put("username", dbSysUser.getUsername()); // 用户名
        resultMap.put("realname", dbSysUser.getRealname()); // 真实姓名
        resultMap.put("token", token); // token
        resultMap.put("email", dbSysUser.getEmail()); // 邮箱
        resultMap.put("sex", dbSysUser.getSex()); // 性别
        resultMap.put("userIcon", dbSysUser.getUserIcon()); // 用户头像
        resultMap.put("createTime", dbSysUser.getCreateTime()); // 创建时间
        resultMap.put("role", dbSysUser.getSysRole()); // 用户角色
        return BaseResult.success("登录成功", resultMap); // 返回成功信息和用户信息
    }

    /**
     * 退出系统
     * @param request HTTP请求对象
     * @return 返回退出结果
     */
    @GetMapping("loginOut")
    public BaseResult loginOut(HttpServletRequest request){
        request.getServletContext().removeAttribute("token"); // 移除token
        return BaseResult.success("退出成功"); // 返回退出成功信息
    }
}
