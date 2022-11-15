package com.fix.mobile.dto;

import lombok.Data;

@Data
public class AccountDTO extends AbstractDTO<String> {

    private String username;
    private String fullName;
    private Boolean gender;
    private String email;
    private String phone;
    private String image;


}