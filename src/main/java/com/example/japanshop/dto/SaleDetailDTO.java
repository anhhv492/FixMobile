package com.example.japanshop.dto;

import com.example.japanshop.entity.Product;
import com.example.japanshop.entity.Sale;

public class SaleDetailDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Sale sale;
    private Product product;

    public SaleDetailDTO() {
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return this.idDetail;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Sale getSale() {
        return this.sale;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}