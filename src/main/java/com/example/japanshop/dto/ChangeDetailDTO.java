package com.example.japanshop.dto;

import com.example.japanshop.entity.OrderDetailProduct;
import com.example.japanshop.entity.Product;
import com.example.japanshop.entity.ProductChange;

public class ChangeDetailDTO extends AbstractDTO<Integer> {
    private Integer idChangeDetail;
    private Product product;
    private OrderDetailProduct orderDetailProduct;
    private ProductChange productChange;

    public ChangeDetailDTO() {
    }

    public void setIdChangeDetail(Integer idChangeDetail) {
        this.idChangeDetail = idChangeDetail;
    }

    public Integer getIdChangeDetail() {
        return this.idChangeDetail;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setOrderDetailProduct(OrderDetailProduct orderDetailProduct) {
        this.orderDetailProduct = orderDetailProduct;
    }

    public OrderDetailProduct getOrderDetailProduct() {
        return this.orderDetailProduct;
    }

    public void setProductChange(ProductChange productChange) {
        this.productChange = productChange;
    }

    public ProductChange getProductChange() {
        return this.productChange;
    }
}