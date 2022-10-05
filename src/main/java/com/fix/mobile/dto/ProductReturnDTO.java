package com.fix.mobile.dto;

import com.fix.mobile.entity.ReturnDetail;
import com.japan.shop.entity.OrderDetailProduct;
import com.japan.shop.entity.Product;
import com.japan.shop.entity.ReturnDetail;

import java.util.List;

public class ProductReturnDTO extends AbstractDTO<Integer> {
    private Integer idReturn;
    private OrderDetailProduct orderDetailProduct;
    private Product product;
    private List<ReturnDetail> returnDetails;

    public ProductReturnDTO() {
    }

    public void setIdReturn(Integer idReturn) {
        this.idReturn = idReturn;
    }

    public Integer getIdReturn() {
        return this.idReturn;
    }

    public void setOrderDetailProduct(OrderDetailProduct orderDetailProduct) {
        this.orderDetailProduct = orderDetailProduct;
    }

    public OrderDetailProduct getOrderDetailProduct() {
        return this.orderDetailProduct;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setReturnDetails(java.util.List<ReturnDetail> returnDetails) {
        this.returnDetails = returnDetails;
    }

    public java.util.List<ReturnDetail> getReturnDetails() {
        return this.returnDetails;
    }
}