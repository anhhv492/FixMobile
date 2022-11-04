package com.fix.mobile.dto;

import com.fix.mobile.entity.Product;

public class ImayProductDTO extends AbstractDTO<Integer> {
	private Integer idImay;
	private String nameImay;
	private int status;
	private Product product;

	public ImayProductDTO() {
	}

	public void setIdImay(Integer idImay) {
		this.idImay = idImay;
	}

	public Integer getIdImay() {
		return this.idImay;
	}

	public void setNameImay(String nameImay) {
		this.nameImay = nameImay;
	}

	public String getNameImay() {
		return this.nameImay;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return this.product;
	}
}