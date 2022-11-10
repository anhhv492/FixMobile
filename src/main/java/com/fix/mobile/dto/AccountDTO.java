package com.fix.mobile.dto;

import com.fix.mobile.entity.Address;
import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.entity.Role;
import com.fix.mobile.entity.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class AccountDTO extends AbstractDTO<String> {

    private String username;
    private String fullName;
    private Boolean gender;
    private String email;
    private String phone;
    private String image;


}