package com.fix.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckProductDTO {
    private List<Integer> checkCapa;
    private List<Integer> checkRam;
    private List<Integer> checkColor;
}
