package com.owpai.server.controller;

import com.owpai.common.constant.JwtClaimsConstant;
import com.owpai.common.properties.JwtProperties;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.AdminDTO;
import com.owpai.pojo.dto.AdminLoginDTO;
import com.owpai.pojo.entity.Admin;
import com.owpai.server.service.AdminService;

import com.owpai.pojo.vo.AdminLoginVO;
import com.owpai.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 * 处理管理员相关的请求，包括登录认证等
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * 验证管理员身份并生成JWT令牌
     *
     * @param adminLoginDTO 登录信息传输对象
     * @return 登录结果，包含JWT令牌和管理员信息
     */
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        // 记录登录日志
        log.info("管理员登录：{}", adminLoginDTO.getUsername());

        // 调用服务层进行登录验证
        Admin admin = adminService.adminLogin(adminLoginDTO);

        // 创建JWT载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getId());

        // 生成JWT令牌
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(), // 密钥
                jwtProperties.getAdminTtl(), // 有效期
                claims // 载荷信息
        );

        // 构建登录响应对象
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .token(token)
                .build();

        // 返回登录成功结果
        return Result.success(adminLoginVO);
    }
    /**
     * admin退出
     */
    @PostMapping("/logout")
    public Result<String> logout(){
        return Result.success();
    }

    /**
     * id 查询admin信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Admin> getById(@PathVariable Long id) {
        Admin admin = adminService.getById();
        return Result.success(admin);
    }

    /**
     * admin信息设置
     * @param adminDTO
     * @return
     */
    @PutMapping("/setup")
    public Result update(@RequestBody AdminDTO adminDTO){
        adminService.update(adminDTO);
        return Result.success();
    }
}
