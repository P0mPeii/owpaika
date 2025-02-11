package com.owpai.server.service;

import com.owpai.pojo.dto.AdminDTO;
import com.owpai.pojo.dto.AdminLoginDTO;
import com.owpai.pojo.entity.Admin;

public interface AdminService {
    Admin adminLogin(AdminLoginDTO adminLoginDTO);

    Admin getById();

    void update(AdminDTO adminDTO);
}