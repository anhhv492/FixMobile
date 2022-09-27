package com.japan.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "return_detail")
public class ReturnDetail {
    @Id
    @Column(name = "id_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetail;

    @Column(name = "date_return")
    private java.sql.Date dateReturn;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_return")
    private ProductReturn productReturn;
}
