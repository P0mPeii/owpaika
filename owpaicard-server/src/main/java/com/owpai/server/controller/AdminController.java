package com.owpai.server.controller;

import com.owpai.common.constant.JwtClaimsConstant;
import com.owpai.common.properties.JwtProperties;
import com.owpai.common.result.Result;
import com.owpai.pojo.dto.AdminLoginDTO;
import com.owpai.pojo.entity.Admin;
import com.owpai.server.service.AdminService;

import com.owpai.pojo.vo.AdminLoginVO;
import com.owpai.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    // 微信登录
    @PostMapping("/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("管理员登录：{}", adminLoginDTO.getUsername());
        Admin admin = adminService.adminLogin(adminLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .token(token)
                .build();
        return Result.success(adminLoginVO);
    }
}
