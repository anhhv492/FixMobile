package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImayProductDTO {
	private Integer idImay;
	private  String name;
	private int status;
	private Product product;
}