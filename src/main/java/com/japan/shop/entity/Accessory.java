package com.japan.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accessories")
public class Accessory {
    @Id
    @Column(name = "id_accessory")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccessory;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "create_date")
    private java.sql.Date createDate;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "image")
    private String image;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "accessory")
    private List<OrderDetailAccessory> orderDetailAccessories;
}
