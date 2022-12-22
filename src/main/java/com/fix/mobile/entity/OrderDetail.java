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
    private Integer status;

    @Column(name="id_sale")
    private Integer idSale;

    @Column(name="price_sale")
    private BigDecimal priceSale;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

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

    @JsonIgnore
    @OneToMany(mappedBy = "orderDetail")
    private List<ImayProduct> imeis;

    @JsonIgnore
    @OneToMany(mappedBy = "orderDetail")
    private List<ProductChange> productChange;
}
