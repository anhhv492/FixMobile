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
@Table(name = "capacity")
public class Capacity {
    @Id
    @Column(name = "id_capacity")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCapacity;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "capacity")
    private List<Product> products;
}
