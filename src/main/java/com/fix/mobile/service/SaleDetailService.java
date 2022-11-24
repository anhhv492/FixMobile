package com.fix.mobile.service;


import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;

import java.util.ArrayList;
import java.util.List;

public interface SaleDetailService extends GenericService<SaleDetail, Integer> {
    void createSaleDetail(String id, Integer idx);
    void deleteSaleDetai(Integer id);

    List<SaleDetail> findByid(Sale sale);

    void updateSaleDetail(String s, Integer idx, Integer id);
}