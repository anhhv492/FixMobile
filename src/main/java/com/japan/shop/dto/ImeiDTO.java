package com.japan.shop.dto;

import com.japan.shop.entity.Product;

public class ImeiDTO extends AbstractDTO<Integer> {
    private Integer idImei;
    private Product product;

    public ImeiDTO() {
    }

    public void setIdImei(Integer idImei) {
        this.idImei = idImei;
    }

    public Integer getIdImei() {
        return this.idImei;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}