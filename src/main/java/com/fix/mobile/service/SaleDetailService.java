package com.fix.mobile.service;


import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;

import java.util.ArrayList;
import java.util.List;

public interface SaleDetailService extends GenericService<SaleDetail, Integer> {
    void createSaleDetail(String id, Integer idx);

    List<SaleDetail> findByid(Sale sale);
}