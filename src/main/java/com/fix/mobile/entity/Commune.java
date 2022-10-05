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
@Table(name = "commune")
public class Commune {
    @Id
    @Column(name = "id_commune")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCommune;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "commune")
    private List<Address> addresses;
}
