package com.japan.shop.dto;

import com.example.japanshop.entity.*;
import com.japan.shop.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProductDTO extends AbstractDTO<Integer> {
    private Integer idProduct;
    private String name;
    private Integer quantity;
    private Date createDate;
    private String camera;
    private BigDecimal price;
    private String size;
    private String note;
    private Boolean status;
    private Ram ram;
    private Color color;
    private Capacity capacity;
    private Category category;
    private List<ChangeDetail> changeDetails;
    private List<ImageDetail> imageDetails;
    private List<Imei> imies;
    private List<InsuranceDetail> insuranceDetails;
    private List<OrderDetailProduct> orderDetailProducts;
    private List<ProductReturn> productReturns;
    private List<SaleDetail> saleDetails;

    public ProductDTO() {
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdProduct() {
        return this.idProduct;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCamera() {
        return this.camera;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getPrice() {
        return this.price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return this.size;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return this.note;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Ram getRam() {
        return this.ram;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public Capacity getCapacity() {
        return this.capacity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setChangeDetails(java.util.List<ChangeDetail> changeDetails) {
        this.changeDetails = changeDetails;
    }

    public java.util.List<ChangeDetail> getChangeDetails() {
        return this.changeDetails;
    }

    public void setImageDetails(java.util.List<ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public java.util.List<ImageDetail> getImageDetails() {
        return this.imageDetails;
    }

    public void setImies(java.util.List<Imei> imies) {
        this.imies = imies;
    }

    public java.util.List<Imei> getImies() {
        return this.imies;
    }

    public void setInsuranceDetails(java.util.List<InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public java.util.List<InsuranceDetail> getInsuranceDetails() {
        return this.insuranceDetails;
    }

    public void setOrderDetailProducts(java.util.List<OrderDetailProduct> orderDetailProducts) {
        this.orderDetailProducts = orderDetailProducts;
    }

    public java.util.List<OrderDetailProduct> getOrderDetailProducts() {
        return this.orderDetailProducts;
    }

    public void setProductReturns(java.util.List<ProductReturn> productReturns) {
        this.productReturns = productReturns;
    }

    public java.util.List<ProductReturn> getProductReturns() {
        return this.productReturns;
    }

    public void setSaleDetails(java.util.List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public java.util.List<SaleDetail> getSaleDetails() {
        return this.saleDetails;
    }
}