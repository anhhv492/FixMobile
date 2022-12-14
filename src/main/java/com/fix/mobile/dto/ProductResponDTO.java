package com.fix.mobile.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponDTO {
    private Integer idProduct;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer totail;
}
