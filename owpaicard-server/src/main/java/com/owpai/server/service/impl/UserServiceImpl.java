package com.owpai.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.owpai.common.exception.BusinessException;
import com.owpai.pojo.dto.UserDTO;
import com.owpai.pojo.entity.User;
import com.owpai.pojo.vo.UserVO;
import com.owpai.server.mapper.UserMapper;
import com.owpai.server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void register(UserDTO userDTO) {
        // 1.参数校验
        if (StrUtil.isBlank(userDTO.getUsername()) || StrUtil.isBlank(userDTO.getPassword())) {
            throw new BusinessException("用户名或密码不能为空");
        }

        // 2.判断用户名是否存在
        Long count = lambdaQuery()
                .eq(User::getUsername, userDTO.getUsername())
                .count();
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 3.实体转换
        User user = BeanUtil.copyProperties(userDTO, User.class);
        user.setStatus(1);

        // 4.保存用户
        save(user);
    }

    @Override
    public String login(String username, String password) {
        // 1.参数校验
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new BusinessException("用户名或密码不能为空");
        }

        // 2.根据用户名和密码查询
        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password)
                .one();
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3.判断用户状态
        if (user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用");
        }

        // 4.返回token（这里简单返回用户id，实际项目中应该使用JWT等token）
        return user.getId().toString();
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        // 1.查询用户
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2.实体转换
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Override
    public void updateUserInfo(Long userId, UserDTO userDTO) {
        // 1.查询用户
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2.实体转换并更新
        BeanUtil.copyProperties(userDTO, user);
        updateById(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        // 1.参数校验
        if (StrUtil.isBlank(oldPassword) || StrUtil.isBlank(newPassword)) {
            throw new BusinessException("密码不能为空");
        }

        // 2.查询用户
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 3.校验旧密码
        if (!oldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        // 4.更新密码
        user.setPassword(newPassword);
        updateById(user);
    }
}