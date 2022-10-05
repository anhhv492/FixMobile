package com.fix.mobile.dto;

import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.entity.InsuranceDetail;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class InsuranceDTO extends AbstractDTO<Integer> {
    private Integer idInsurance;
    private Integer name;
    private Date dateStart;
    private Date dateEnd;
    private Integer quantity;
    private BigDecimal price;
    private List<InsuranceDetail> insuranceDetails;

    public InsuranceDTO() {
    }

    public void setIdInsurance(Integer idInsurance) {
        this.idInsurance = idInsurance;
    }

    public Integer getIdInsurance() {
        return this.idInsurance;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getName() {
        return this.name;
    }

    public void setDateStart(java.sql.Date dateStart) {
        this.dateStart = dateStart;
    }

    public java.sql.Date getDateStart() {
        return this.dateStart;
    }

    public void setDateEnd(java.sql.Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public java.sql.Date getDateEnd() {
        return this.dateEnd;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getPrice() {
        return this.price;
    }

    public void setInsuranceDetails(java.util.List<InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public java.util.List<InsuranceDetail> getInsuranceDetails() {
        return this.insuranceDetails;
    }
}