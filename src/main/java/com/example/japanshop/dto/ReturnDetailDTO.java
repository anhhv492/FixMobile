package com.example.japanshop.dto;

import com.example.japanshop.entity.ProductReturn;

import java.math.BigDecimal;
import java.sql.Date;

public class ReturnDetailDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Date dateReturn;
    private BigDecimal price;
    private String note;
    private ProductReturn productReturn;

    public ReturnDetailDTO() {
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return this.idDetail;
    }

    public void setDateReturn(java.sql.Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public java.sql.Date getDateReturn() {
        return this.dateReturn;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getPrice() {
        return this.price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setProductReturn(ProductReturn productReturn) {
        this.productReturn = productReturn;
    }

    public ProductReturn getProductReturn() {
        return this.productReturn;
    }
}