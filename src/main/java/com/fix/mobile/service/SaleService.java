package com.fix.mobile.service;


import com.fix.mobile.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    public Sale add(Sale sale);

    public Sale update(Sale sale);

    public Sale delete(Integer id);

    public List<Sale> getall(Integer status);
    public Object getMaxSale();
}