package com.japan.shop.entity;

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
@Table(name = "product_return")
public class ProductReturn {
    @Id
    @Column(name = "id_return")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReturn;

    @ManyToOne
    @JoinColumn(name = "id_detail")
    private OrderDetailProduct orderDetailProduct;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "productReturn")
    private List<ReturnDetail> returnDetails;
}
