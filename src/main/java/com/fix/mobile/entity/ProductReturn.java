package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_return")
public class ProductReturn {
    @Id
    @Column(name = "id_return")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReturn;

    @Column(name = "date_return")
    private Date dateReturn;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_detail")
    private OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
}
