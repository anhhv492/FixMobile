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
@Table(name = "images")
public class Image {
    @Id
    @Column(name = "id_image")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImage;

    @Column(name = "name")
    private String name;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "image")
    private List<ImageDetail> imageDetails;
}
