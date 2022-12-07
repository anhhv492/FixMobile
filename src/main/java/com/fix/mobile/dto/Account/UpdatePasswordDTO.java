package com.fix.mobile.dto.Account;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String oldPassword;
    private String password;
}
