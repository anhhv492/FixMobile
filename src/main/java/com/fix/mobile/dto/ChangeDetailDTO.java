package com.fix.mobile.dto;

import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;
import com.fix.mobile.entity.ProductChange;

public class ChangeDetailDTO extends AbstractDTO<Integer> {
    private Integer idChangeDetail;
    private Product product;
    private OrderDetail orderDetail;
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

    public void setOrderDetailProduct(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public OrderDetail getOrderDetailProduct() {
        return this.orderDetail;
    }

    public void setProductChange(ProductChange productChange) {
        this.productChange = productChange;
    }

    public ProductChange getProductChange() {
        return this.productChange;
    }
}