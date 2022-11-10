package com.fix.mobile.dto;

import lombok.Data;

@Data
public class SingUpDTO {
    private String username;
    private String password;
    private String fullName;
    private Boolean gender;
    private String email;
    private String phone;
}
