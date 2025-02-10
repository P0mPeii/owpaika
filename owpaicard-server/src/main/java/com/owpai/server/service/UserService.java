package com.owpai.server.service;

import com.owpai.pojo.dto.UserDTO;
import com.owpai.pojo.vo.UserVO;

public interface UserService {
    /**
     * 用户注册
     */
    void register(UserDTO userDTO);

    /**
     * 用户登录
     */
    String login(String username, String password);

    /**
     * 获取用户信息
     */
    UserVO getUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    void updateUserInfo(Long userId, UserDTO userDTO);

    /**
     * 修改密码
     */
    void updatePassword(Long userId, String oldPassword, String newPassword);
}