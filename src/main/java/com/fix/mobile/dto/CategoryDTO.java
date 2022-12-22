package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO extends AbstractDTO<Integer> {

    private Integer type;
    private String name;

}