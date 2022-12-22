package com.fix.mobile.dto;

import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;
import com.fix.mobile.entity.ProductChange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDetailDTO extends AbstractDTO<Integer> {
    private Integer idChangeDetail;
    private Product product;
    private OrderDetail orderDetail;
    private ProductChange productChange;
    private List<String> imaysp;

}