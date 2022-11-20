package com.fix.mobile.dto.Account;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
