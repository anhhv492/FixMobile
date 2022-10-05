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
@Table(name = "sale")
public class Sale {
    @Id
    @Column(name = "id_sale")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSale;

    @Column(name = "name")
    private String name;

    @Column(name = "type_sale")
    private String typeSale;

    @Column(name = "create_start")
    private java.sql.Date createStart;

    @Column(name = "create_end")
    private java.sql.Date createEnd;

    @Column(name = "voucher")
    private String voucher;

    @Column(name = "value_min")
    private BigDecimal valueMin;

    @Column(name = "money_sale")
    private BigDecimal moneySale;

    @Column(name = "quantity_use")
    private Integer quantityUse;

    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "sale")
    private List<SaleDetail> saleDetails;
}
