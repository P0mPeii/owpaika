package com.owpai.server.controller;

import com.owpai.common.result.Result;
import com.owpai.pojo.dto.UserDTO;
import com.owpai.pojo.vo.UserVO;
import com.owpai.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        return Result.success(token);
    }

    @GetMapping("/info/{userId}")
    public Result<UserVO> getUserInfo(@PathVariable Long userId) {
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }

    @PutMapping("/info/{userId}")
    public Result<Void> updateUserInfo(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        userService.updateUserInfo(userId, userDTO);
        return Result.success();
    }

    @PutMapping("/password/{userId}")
    public Result<Void> updatePassword(@PathVariable Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success();
    }
}