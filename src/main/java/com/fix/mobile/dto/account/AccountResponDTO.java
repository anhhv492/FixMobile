package com.fix.mobile.dto.account;

import lombok.Data;

import java.util.Date;

@Data
public class AccountResponDTO {
    private String username;
    private String fullName;
    private Boolean gender;
    private Integer status;
    private String password;
    private String email;
    private String phone;
    private String image;
    private Integer role;
    private Date createDate;
}
