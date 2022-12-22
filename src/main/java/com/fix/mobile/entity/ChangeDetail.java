package com.fix.mobile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "change_detail")
public class ChangeDetail {
    @Id
    @Column(name = "id_change_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChangeDetail;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_order_detail")
    private OrderDetail orderDetail;
    @ManyToOne
    @JoinColumn(name = "id_change")
    private ProductChange productChange;



}
