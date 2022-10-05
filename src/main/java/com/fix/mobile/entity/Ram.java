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
@Table(name = "ram")
public class Ram {
    @Id
    @Column(name = "id_ram")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRam;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "ram")
    private List<Product> products;
}
