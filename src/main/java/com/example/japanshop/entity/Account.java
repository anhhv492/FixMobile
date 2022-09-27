package com.example.japanshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "create_date")
    private java.sql.Date createDate;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Address> addresses;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<InsuranceDetail> insuranceDetails;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Order> orders;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductChange> productChanges;
}
