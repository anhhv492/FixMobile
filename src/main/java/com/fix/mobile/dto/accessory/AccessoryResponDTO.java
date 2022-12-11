package com.fix.mobile.dto.accessory;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class AccessoryResponDTO {
    private String name;
    private Integer quantity;
    private String color;
    private BigDecimal price;
    private String image;
    private String note;
    private Integer category;

}
