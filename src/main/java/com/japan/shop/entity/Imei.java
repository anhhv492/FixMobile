package com.japan.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "imei")
public class Imei {
    @Id
    @Column(name = "id_imei")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImei;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
