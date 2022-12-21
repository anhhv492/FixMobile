package com.fix.mobile.dto;

import com.fix.mobile.entity.Capacity;
import com.fix.mobile.entity.Color;
import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.Ram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Integer id;
    private String name;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private List<Capacity> capa;
    private List<Ram> ram;
    private List<Color> color;
    private List<String> images;
}
