package com.fix.mobile.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
		"username"
})})
public class Account {
    @Id
    @Column(name = "username")
    @NotBlank
    @Size(min = 3, max = 8)
    private String username;

    @Column(name = "password")
    
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "email")
    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @Column(name = "phone")
    @NotBlank
//    @Size(min = 10, max = 10)
    private String phone;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "image")
    @Lob
    private String image;

    @Column(name = "status")
    private int status;

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
	public Role getRole() {
		return role;
	}
	
	
    

	
   
}
