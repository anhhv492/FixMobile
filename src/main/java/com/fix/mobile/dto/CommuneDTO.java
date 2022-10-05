package com.fix.mobile.dto;

import com.fix.mobile.entity.Address;

import java.util.List;

public class CommuneDTO extends AbstractDTO<Integer> {
    private Integer idCommune;
    private String name;
    private List<Address> addresses;

    public CommuneDTO() {
    }

    public void setIdCommune(Integer idCommune) {
        this.idCommune = idCommune;
    }

    public Integer getIdCommune() {
        return this.idCommune;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddresses(java.util.List<Address> addresses) {
        this.addresses = addresses;
    }

    public java.util.List<Address> getAddresses() {
        return this.addresses;
    }
}