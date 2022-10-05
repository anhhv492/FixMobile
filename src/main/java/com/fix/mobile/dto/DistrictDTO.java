package com.fix.mobile.dto;

import com.fix.mobile.entity.Address;

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

    public void setAddresses(java.util.List<Address> addresses) {
        this.addresses = addresses;
    }

    public java.util.List<Address> getAddresses() {
        return this.addresses;
    }
}