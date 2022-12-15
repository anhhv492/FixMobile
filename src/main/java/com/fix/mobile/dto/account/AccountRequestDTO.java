package com.fix.mobile.dto.account;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AccountRequestDTO {
    private String username;
    private String fullName;
    private Boolean gender;
    private Integer status;
    private String password;
    private String email;
    private String phone;
    private MultipartFile image;
    private Integer role;
}
