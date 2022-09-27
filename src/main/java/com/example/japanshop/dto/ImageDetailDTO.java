package com.example.japanshop.dto;

import com.example.japanshop.entity.Image;
import com.example.japanshop.entity.Product;

public class ImageDetailDTO extends AbstractDTO<Integer> {
    private Integer idDetail;
    private Image image;
    private Product product;

    public ImageDetailDTO() {
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getIdDetail() {
        return this.idDetail;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}