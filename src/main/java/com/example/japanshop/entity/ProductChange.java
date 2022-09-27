package com.example.japanshop.entity;

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
@Table(name = "product_change")
public class ProductChange {
    @Id
    @Column(name = "id_change")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChange;

    @Column(name = "imei")
    private Integer imei;

    @Column(name = "data_change")
    private Integer dataChange;

    @Column(name = "note")
    private String note;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "productChange")
    private List<ChangeDetail> changeDetails;
}
