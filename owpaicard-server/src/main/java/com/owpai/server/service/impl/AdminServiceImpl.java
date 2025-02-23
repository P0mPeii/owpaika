package com.owpai.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.constant.MessageConstant;
import com.owpai.common.exception.LoginFailedException;
import com.owpai.pojo.dto.AdminDTO;
import com.owpai.pojo.dto.AdminLoginDTO;
import com.owpai.pojo.entity.Admin;
import com.owpai.server.mapper.AdminMapper;
import com.owpai.server.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin adminLogin(AdminLoginDTO adminLoginDTO) {
        // 根据用户名查询管理员
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, adminLoginDTO.getUsername());
        Admin admin = getOne(queryWrapper);

        // 判断管理员是否存在
        if (admin == null) {
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 使用MD5验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(adminLoginDTO.getPassword().getBytes());
        if (!encryptedPassword.equals(admin.getPassword())) {
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }

        return admin;
    }

    @Override
    public Admin getById(Long id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

    @Override
    public void update(AdminDTO adminDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDTO, admin);

        // 如果密码不为空，进行MD5加密
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            String encryptedPassword = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
            admin.setPassword(encryptedPassword);
        }

        updateById(admin);
    }
}
