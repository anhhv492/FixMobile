package com.fix.mobile.dto;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.ProductChange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductChangeDTO{
    private Integer idChange;
    private Integer imei;
    private Integer dataChange;
    private String note;
    private BigDecimal price;
    private Boolean status;
    private Account account;
    private List<ChangeDetail> changeDetails;
    private List<Image> listImage;
    private List<MultipartFile> files;


    public ProductChangeDTO(Integer idChange, Integer imei, Integer dataChange, String note, BigDecimal price, Boolean status, Account account, List<Image> listImage, List<MultipartFile> files) {
        this.idChange = idChange;
        this.imei = imei;
        this.dataChange = dataChange;
        this.note = note;
        this.price = price;
        this.status = status;
        this.account = account;
        this.listImage = listImage;
        this.files = files;
    }

}