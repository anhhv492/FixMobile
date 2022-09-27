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
@Table(name = "province")
public class Province {
    @Id
    @Column(name = "id_province")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProvince;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "province")
    private List<Address> addresses;
}

