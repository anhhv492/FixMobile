package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
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
    private Date createDate;

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
    private List<OrderDetail> orderDetail;

    public Accessory(String name, Integer quantity, Date createDate, String color, BigDecimal price, Boolean status, String note, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.createDate = createDate;
        this.color = color;
        this.price = price;
        this.status = status;
        this.note = note;
        this.category = category;
    }

}
