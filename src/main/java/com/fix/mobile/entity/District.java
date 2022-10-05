package com.fix.mobile.entity;

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
@Table(name = "district")
public class District {
    @Id
    @Column(name = "id_district")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDistrict;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private List<Address> addresses;
}
