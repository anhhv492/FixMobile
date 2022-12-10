package com.fix.mobile.dto.account;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String oldPassword;
    private String password;
}
