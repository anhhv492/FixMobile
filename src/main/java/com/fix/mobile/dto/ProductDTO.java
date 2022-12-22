package com.fix.mobile.dto;

import com.fix.mobile.entity.Capacity;
import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProductDTO extends AbstractDTO<Integer> {
    private Integer idProduct;
    private String name;
    private String imei;
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
    private List<InsuranceDetail> insuranceDetails;
    private List<OrderDetail> orderDetails;
    private List<ProductReturn> productReturns;
    private List<SaleDetail> saleDetails;
    private Image image;

    public ProductDTO() {
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ChangeDetail> getChangeDetails() {
        return changeDetails;
    }

    public void setChangeDetails(List<ChangeDetail> changeDetails) {
        this.changeDetails = changeDetails;
    }

    public List<InsuranceDetail> getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(List<InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<ProductReturn> getProductReturns() {
        return productReturns;
    }

    public void setProductReturns(List<ProductReturn> productReturns) {
        this.productReturns = productReturns;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}