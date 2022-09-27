package com.example.japanshop.dto;

import com.example.japanshop.entity.*;

import java.sql.Date;
import java.util.List;

public class AccountDTO extends AbstractDTO<String> {
    private String username;
    private String password;
    private String fullName;
    private Boolean gender;
    private String email;
    private String phone;
    private Date createDate;
    private String image;
    private Boolean status;
    private Role role;
    private List<Address> addresses;
    private List<InsuranceDetail> insuranceDetails;
    private List<Order> orders;
    private List<ProductChange> productChanges;

    public AccountDTO() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getGender() {
        return this.gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }

    public java.sql.Date getCreateDate() {
        return this.createDate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return this.role;
    }

    public void setAddresses(java.util.List<com.example.japanshop.entity.Address> addresses) {
        this.addresses = addresses;
    }

    public java.util.List<com.example.japanshop.entity.Address> getAddresses() {
        return this.addresses;
    }

    public void setInsuranceDetails(java.util.List<com.example.japanshop.entity.InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public java.util.List<com.example.japanshop.entity.InsuranceDetail> getInsuranceDetails() {
        return this.insuranceDetails;
    }

    public void setOrders(java.util.List<com.example.japanshop.entity.Order> orders) {
        this.orders = orders;
    }

    public java.util.List<com.example.japanshop.entity.Order> getOrders() {
        return this.orders;
    }

    public void setProductChanges(java.util.List<com.example.japanshop.entity.ProductChange> productChanges) {
        this.productChanges = productChanges;
    }

    public java.util.List<com.example.japanshop.entity.ProductChange> getProductChanges() {
        return this.productChanges;
    }
}