package com.japan.shop.dto;

import com.japan.shop.entity.Account;
import com.japan.shop.entity.Commune;
import com.japan.shop.entity.District;
import com.japan.shop.entity.Province;

public class AddressDTO extends AbstractDTO<Integer> {
    private Integer idAddress;
    private String addressDetail;
    private String personTake;
    private String phoneTake;
    private Province province;
    private District district;
    private Commune commune;
    private Account account;

    public AddressDTO() {
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getIdAddress() {
        return this.idAddress;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressDetail() {
        return this.addressDetail;
    }

    public void setPersonTake(String personTake) {
        this.personTake = personTake;
    }

    public String getPersonTake() {
        return this.personTake;
    }

    public void setPhoneTake(String phoneTake) {
        this.phoneTake = phoneTake;
    }

    public String getPhoneTake() {
        return this.phoneTake;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Province getProvince() {
        return this.province;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public Commune getCommune() {
        return this.commune;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }
}