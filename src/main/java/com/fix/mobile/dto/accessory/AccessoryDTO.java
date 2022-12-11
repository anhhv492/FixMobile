package com.fix.mobile.dto.accessory;

import com.fix.mobile.dto.AbstractDTO;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.OrderDetail;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class AccessoryDTO extends AbstractDTO<Integer> {
    private String name;
    private Integer quantity;
    private String color;
    private BigDecimal price;
    private MultipartFile image;
    private String note;
    private Integer category;

}