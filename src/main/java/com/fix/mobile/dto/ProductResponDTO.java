package com.fix.mobile.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponDTO {
    private Integer idProduct;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer totail;
}
