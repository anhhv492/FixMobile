package com.fix.mobile.dto;

import lombok.Data;

@Data
public class ResponseTokenDTO {
    private String token;
    private String username;

    public ResponseTokenDTO(String token, String username) {
        this.token = token;

        this.username =username;
    }
}
