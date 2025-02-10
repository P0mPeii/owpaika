package com.owpai.pojo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
}