package com.example.japanshop.entity;

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

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
