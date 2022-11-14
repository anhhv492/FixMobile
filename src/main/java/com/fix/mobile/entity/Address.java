package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAddress;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "person_take")
    private String personTake;

    @Column(name = "phone_take")
    private String phoneTake;

    @Column(name = "address_take")
    private String addressTake;

    @JsonIgnore
    @OneToOne(mappedBy = "address_id")
    private Account account_address;


    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
}
