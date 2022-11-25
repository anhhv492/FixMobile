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
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Integer status;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private Boolean type;

    @Column(name = "money_sale")
    private BigDecimal moneySale;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "id_sale")
    private Sale sale;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
