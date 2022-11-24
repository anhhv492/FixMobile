package com.fix.mobile.dto;

import com.fix.mobile.entity.Account;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductChangeDTO extends AbstractDTO<Integer> {
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
}