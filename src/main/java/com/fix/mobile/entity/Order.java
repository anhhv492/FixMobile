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
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @Column(name = "create_date")
    private java.sql.Date createDate;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private Boolean type;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetailAccessory> orderDetailAccessories;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetailProduct> orderDetailProducts;
}
