package com.example.japanshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurance_product")
public class InsuranceProduct {
    @Id
    @Column(name = "id_insurance")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInsurance;

    @Column(name = "note")
    private String note;

    @Column(name = "create_start")
    private java.sql.Date createStart;

    @Column(name = "create_end")
    private java.sql.Date createEnd;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "id_detail")
    private OrderDetailProduct orderDetailProduct;

}
