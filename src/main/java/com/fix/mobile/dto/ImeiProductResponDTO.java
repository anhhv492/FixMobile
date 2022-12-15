package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;
import lombok.Data;


@Data
public class ImeiProductResponDTO {
    private Integer idImay;
    private String name;
    private int status;
    private Product product;
}
