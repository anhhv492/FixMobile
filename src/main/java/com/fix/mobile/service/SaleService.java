package com.fix.mobile.service;


import com.fix.mobile.entity.Sale;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    public Sale add(Sale sale);

    public Sale update(Sale sale);

    public void delete(Integer id);

    public List<Sale> getall(Integer status);

    public Sale findByid(Integer id);

    public Integer getLimit1Sale();

    Page<Sale> getByPage(int pageNumber, int maxRecord, Integer Status, String share,String type);
}