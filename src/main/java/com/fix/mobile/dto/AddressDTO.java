package com.fix.mobile.dto;


import lombok.Data;

@Data

public class AddressDTO extends AbstractDTO<Integer> {

    private Integer idAddress;
    private String addressDetail;
    private String personTake;
    private String phoneTake;
    private String addressTake;
    private Integer provinceId;
    private Integer districtId;
    private Integer wardId;
    private String province;
    private String district;
    private String ward;


}