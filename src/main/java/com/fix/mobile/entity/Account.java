package com.fix.mobile.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Column(name = "username", length = 250)
    @NotBlank(message = "Tên tài khoản không được trống")
    @Size(min = 3, max = 250, message = "Tên tài khoản từ 3-250 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được trống")
    @Column(name = "password", length = 250)
    private String password;

    @NotBlank(message = "Họ tên không được trống")
    @Column(name = "full_name", length = 250)
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "email", length = 250)
    @Email(message = "Email sai định dạng")
    @NotBlank(message = "Không được để trống")
    @Size(max = 250)
    private String email;

    @Column(name = "phone", length = 10)
    @NotBlank(message = "SĐT không được trống")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "SĐT sai định dạng")
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "address_id" , referencedColumnName = "id_address")
    private Address address_id;
	public Role getRole() {
		return role;
	}

    public Account(String username, String password, String fullName, Boolean gender, String email, Date date, Role role , String phone ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.createDate = date;
        this.role = role;
        this.phone = phone;
    }

}
