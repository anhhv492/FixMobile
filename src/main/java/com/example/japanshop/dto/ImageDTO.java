package com.example.japanshop.dto;

import com.example.japanshop.entity.ImageDetail;

import java.util.List;

public class ImageDTO extends AbstractDTO<Integer> {
    private Integer idImage;
    private String name;
    private List<ImageDetail> imageDetails;

    public ImageDTO() {
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public Integer getIdImage() {
        return this.idImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setImageDetails(java.util.List<com.example.japanshop.entity.ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public java.util.List<com.example.japanshop.entity.ImageDetail> getImageDetails() {
        return this.imageDetails;
    }
}