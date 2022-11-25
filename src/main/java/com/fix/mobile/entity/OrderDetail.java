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
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @Column(name = "id_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_imei")
    private ImayProduct imei;

    @ManyToOne
    @JoinColumn(name = "id_accessory")
    private Accessory accessory;
    //
    @JsonIgnore
    @OneToMany(mappedBy = "orderDetail")
    private List<ChangeDetail> changeDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "orderDetail")
    private List<ProductReturn> productReturns;
}
