package com.fix.mobile.dto;

import com.fix.mobile.entity.Account;

import java.util.List;

public class RoleDTO extends AbstractDTO<Integer> {
    private Integer idRole;
    private String name;
    private List<Account> accounts;

    public RoleDTO() {
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getIdRole() {
        return this.idRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAccounts(java.util.List<Account> accounts) {
        this.accounts = accounts;
    }

    public java.util.List<Account> getAccounts() {
        return this.accounts;
    }
}