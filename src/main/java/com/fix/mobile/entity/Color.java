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
@Table(name = "color")
public class Color {
    @Id
    @Column(name = "id_color")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idColor;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "color")
    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "color")
    private List<Accessory> accessory;
}
