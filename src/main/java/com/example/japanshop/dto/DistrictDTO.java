package com.example.japanshop.dto;

import com.example.japanshop.entity.Address;

import java.util.List;

public class DistrictDTO extends AbstractDTO<Integer> {
    private Integer idDistrict;
    private String name;
    private List<Address> addresses;

    public DistrictDTO() {
    }

    public void setIdDistrict(Integer idDistrict) {
        this.idDistrict = idDistrict;
    }

    public Integer getIdDistrict() {
        return this.idDistrict;
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