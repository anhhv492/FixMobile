package com.fix.mobile.dto;


import lombok.Data;

@Data

public class AddressDTO extends AbstractDTO<Integer> {

    private Integer idAddress;
    private String addressDetail;
    private String personTake;
    private String phoneTake;
    private String addressTake;



}