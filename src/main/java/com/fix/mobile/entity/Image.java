package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
    @JsonBackReference
    public Product getProduct(){
        return product;
    }

    @ManyToOne
    @JoinColumn(name = "id_productChange")
    private ProductChange productChange;

    @JsonBackReference
    public ProductChange getProductChange(){
        return productChange;
    }


}
