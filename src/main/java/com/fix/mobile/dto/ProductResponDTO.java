package com.fix.mobile.dto;

import com.fix.mobile.entity.Capacity;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Color;
import com.fix.mobile.entity.Ram;
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
    private String note;
    private int status;
    private Ram ram;
    private Color color;
    private Capacity capacity;
    private Category category;
    private String camera;
}
