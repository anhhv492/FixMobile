package com.fix.mobile.entity;

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
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "imei")
    private String imei;

    @Column(name = "create_date")
    private java.sql.Date createDate;

    @Column(name = "camera")
    private String camera;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "size")
    private String size;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_ram")
    private Ram ram;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "id_capacity")
    private Capacity capacity;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ChangeDetail> changeDetails;


    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<InsuranceDetail> insuranceDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductReturn> productReturns;

}
