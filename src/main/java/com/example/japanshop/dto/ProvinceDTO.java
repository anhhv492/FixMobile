package com.example.japanshop.dto;

import com.example.japanshop.entity.Address;

import java.util.List;

public class ProvinceDTO extends AbstractDTO<Integer> {
    private Integer idProvince;
    private String name;
    private List<Address> addresses;

    public ProvinceDTO() {
    }

    public void setIdProvince(Integer idProvince) {
        this.idProvince = idProvince;
    }

    public Integer getIdProvince() {
        return this.idProvince;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddresses(java.util.List<com.example.japanshop.entity.Address> addresses) {
        this.addresses = addresses;
    }

    public java.util.List<com.example.japanshop.entity.Address> getAddresses() {
        return this.addresses;
    }
}