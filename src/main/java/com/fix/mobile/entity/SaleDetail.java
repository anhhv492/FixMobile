package com.fix.mobile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_detail")
public class SaleDetail {
    @Id
    @Column(name = "id_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetail;

    @Column(name = "id_product")
    private Integer idProduct;

    @Column(name = "username")
    private String userName;

    @Column(name = "id_accessory")
    private Integer idAccessory;

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private Sale sale;
    
}
