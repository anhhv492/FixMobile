package com.japan.shop.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProduct;
	
	@Column(name = "nameproduct", length = 50)
	private String name;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column (name = "status")
	private boolean status;
	
	

}
