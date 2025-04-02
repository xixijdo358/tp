package com.example.pojo.controller;

// 导入所需的类和包
import com.example.base.BaseResult;
import com.example.email.MailService;
import com.example.exception.BadRequestException;
import com.example.pojo.domain.SysUser;
import com.example.pojo.service.ISysUserService;
import com.example.pojo.service.dto.UserQueryCriteria;
import com.example.pojo.vo.ModifyPwdModel;
import com.example.utils.HutoolJWTUtil;
import com.example.utils.NativeFileUtil;
import com.example.utils.PageVo;
import com.example.utils.XuedenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**功能描述：系统用户前端控制器
 * 该控制器负责处理与用户相关的HTTP请求，包括获取用户列表、添加用户、获取用户详情、更新用户信息、删除用户、上传头像、修改个人信息、发送验证码、校验验证码、更改绑定邮箱和更新个人密码等功能。
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 头像存放路径
     */
    @Value("${user.icon}")
    private String userIcon;

    /**
     * 邮件发送方
     */
    @Value("${spring.mail.username}")
    private String from;

    private final ISysUserService sysUserService; // 用户服务接口

    private final MailService mailService; // 邮件服务接口

    // 构造函数，注入用户服务和邮件服务
    public UserController(ISysUserService sysUserService, MailService mailService) {
        this.sysUserService = sysUserService;
        this.mailService = mailService;
    }

    /**
     * 获取用户列表数据
     * @param queryCriteria 查询条件
     * @param pageVo 分页信息
     * @return 返回用户列表的响应实体
     */
    @GetMapping
    public ResponseEntity<Object> getList(UserQueryCriteria queryCriteria, PageVo pageVo){
        // 创建分页请求对象，设置当前页和每页大小，并按ID降序排序
        Pageable pageable = PageRequest.of(pageVo.getPageIndex()-1,pageVo.getPageSize(), Sort.Direction.DESC, "id");
        // 返回用户列表
        return new ResponseEntity<>(sysUserService.getList(queryCriteria,pageable), HttpStatus.OK);
    }

    /**
     * 添加用户信息
     * @param sysUser 用户对象
     * @return 返回添加结果
     */
    @PostMapping
    public BaseResult addUser(@RequestBody SysUser sysUser){
        boolean result= sysUserService.addUser(sysUser); // 调用服务添加用户
        if(result){
            return BaseResult.success("添加成功"); // 返回成功信息
        }else {
            return BaseResult.fail("添加失败"); // 返回失败信息
        }
    }

    /**
     * 根据ID获取用户详情信息
     * @param id 用户ID
     * @return 返回用户详情
     */
    @GetMapping("/{id}")
    public BaseResult detail(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("获取信息失败"); // 如果ID为空，抛出异常
        }
        SysUser dbSysUser = sysUserService.getById(id); // 根据ID获取用户信息
        return BaseResult.success(dbSysUser); // 返回用户详情
    }

    /**
     * 更新用户信息
     * @param sysUser 用户对象
     * @return 返回更新结果
     */
    @PutMapping
    public BaseResult editUser(@RequestBody SysUser sysUser){
        sysUserService.editUser(sysUser); // 调用服务更新用户信息
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 根据ID删除用户信息
     * @param id 用户ID
     * @return 返回删除结果
     */
    @DeleteMapping("/{id}")
    public BaseResult delete(@PathVariable Long id){
        if(null==id){
            throw new BadRequestException("删除信息失败"); // 如果ID为空，抛出异常
        }
        sysUserService.deleteById(id); // 调用服务删除用户
        return BaseResult.success("删除成功"); // 返回成功信息
    }

    /**
     * 上传头像
     * @param file 上传的文件
     * @return 返回上传结果
     */
    @PostMapping("userIcon")
    public BaseResult uploadFile(@RequestParam("fileResource") MultipartFile file){
        if(file==null){
            return BaseResult.fail("文件不能为空"); // 如果文件为空，返回失败信息
        }
        try {
            String tempFileResource = NativeFileUtil.uploadUserIcon(file,userIcon); // 调用工具类上传头像
            Map<String,Object> result = new HashMap<>();
            result.put("userIcon",tempFileResource); // 将上传的头像路径放入结果中
            return BaseResult.success(result); // 返回成功信息
        }catch (Exception e){
            e.printStackTrace(); // 打印异常信息
            return BaseResult.fail(e.getMessage()); // 返回失败信息
        }
    }

    /**
     * 修改个人信息
     * @param sysUser 用户对象
     * @return 返回更新结果
     */
    @PutMapping("updateInfo")
    public BaseResult updateInfo(@RequestBody SysUser sysUser, HttpServletRequest request){
        // 获取登录用户Id
        String token = (String)request.getServletContext().getAttribute("token");
        Long userId = HutoolJWTUtil.parseToken(token); // 解析token获取用户ID
        sysUser.setId(userId); // 设置用户ID
        sysUserService.editUser(sysUser); // 调用服务更新用户信息
        return BaseResult.success("更新成功"); // 返回成功信息
    }

    /**
     * 发送验证码
     * @param email 用户邮箱
     * @return 返回发送结果
     */
    @GetMapping("sendEmail")
    public BaseResult sendEmail(@RequestParam("email")String email, HttpServletRequest request){
        // 发送到旧邮箱
        if(email==null||email==""){
            // 获取登录用户Id
            String token = (String)request.getServletContext().getAttribute("token");
            Long userId = HutoolJWTUtil.parseToken(token); // 解析token获取用户ID
            SysUser dbSysUser = sysUserService.getById(userId); // 根据ID获取用户信息
            email = dbSysUser.getEmail(); // 获取用户邮箱
        }
        int code = XuedenUtil.randomSixNums(); // 生成六位随机验证码
        String content = "验证码："+code+"此验证码用于更换邮箱绑定，请勿将验证码告知他人，有效期3分钟，请妥善保管。"; // 验证码内容
        mailService.sendSimpleMail(from,email,email,"修改邮箱验证码",content); // 发送邮件
        request.getServletContext().setAttribute("code",code); // 将验证码存入session
        return BaseResult.success(); // 返回成功信息
    }

    /**
     * 校验验证码
     * @param code 验证码
     * @return 返回校验结果
     */
    @GetMapping("verifyCode")
    public BaseResult verifyCode(@RequestParam("code")Integer code, HttpServletRequest request){
        if(code==null){
            return BaseResult.fail("验证码不存在！"); // 如果验证码为空，返回失败信息
        }
        System.out.println("request.getServletContext().getAttribute(\"code\"):::"+request.getServletContext().getAttribute("code")); // 打印session中的验证码
        Integer sessionCode = (Integer) request.getServletContext().getAttribute("code"); // 获取session中的验证码
        if(sessionCode==null){
            return BaseResult.fail("验证码已过期！"); // 如果session中的验证码为空，返回失败信息
        }
        if(!sessionCode.equals(code)){
            return BaseResult.fail("验证码输入不正确，请重新输入！"); // 如果验证码不匹配，返回失败信息
        }
        return BaseResult.success(); // 返回成功信息
    }

    /**
     * 更改绑定邮箱
     * @param code 验证码
     * @return 返回更新结果
     */
    @PutMapping("updateEmail")
    public BaseResult updateEmail(@RequestParam("code")Integer code,@RequestParam("email")String email, HttpServletRequest request){
        if(code==null|| email==null){
            return BaseResult.fail("验证码或者邮箱不存在！"); // 如果验证码或邮箱为空，返回失败信息
        }
        Integer sessionCode = (Integer) request.getServletContext().getAttribute("code"); // 获取session中的验证码
        if(sessionCode==null){
            return BaseResult.fail("验证码已过期！"); // 如果session中的验证码为空，返回失败信息
        }
        if(!sessionCode.equals(code)){
            return BaseResult.fail("验证码输入不正确，请重新输入！"); // 如果验证码不匹配，返回失败信息
        }
        // 获取登录用户Id
        String token = (String)request.getServletContext().getAttribute("token");
        Long userId = HutoolJWTUtil.parseToken(token); // 解析token获取用户ID
        SysUser tempSysUser = new SysUser(); // 创建用户对象
        tempSysUser.setEmail(email); // 设置新邮箱
        tempSysUser.setId(userId); // 设置用户ID
        sysUserService.editUser(tempSysUser); // 调用服务更新用户信息
        return BaseResult.success(); // 返回成功信息
    }

    /**
     * 更新个人密码
     * @param modifyPwdModel 密码修改模型
     * @return 返回更新结果
     */
    @PutMapping("updatePwd")
    public BaseResult updatePwd(@RequestBody ModifyPwdModel modifyPwdModel, HttpServletRequest request){
        if(modifyPwdModel==null){
            return BaseResult.fail("更新失败！"); // 如果密码修改模型为空，返回失败信息
        }
        // 获取登录用户Id
        String token = (String)request.getServletContext().getAttribute("token");
        Long userId = HutoolJWTUtil.parseToken(token); // 解析token获取用户ID
        modifyPwdModel.setUserId(userId); // 设置用户ID
        boolean result=sysUserService.updatePwd(modifyPwdModel); // 调用服务更新密码
        if(result){
            return BaseResult.success("更新成功！"); // 返回成功信息
        }else {
            return BaseResult.fail("更新失败！"); // 返回失败信息
        }
    }

}
