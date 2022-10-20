package com.fix.mobile.service.impl;

import com.fix.mobile.exception.SaleException;
import com.fix.mobile.exception.StaffException;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.service.SaleService;
import com.fix.mobile.entity.Sale;
//import org.springframework.security.core.Authentication; cuongnd edit
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository dao;

    @Override
    public Sale add(Sale sale) {
        dao.findByName(sale.getName()).ifPresent(e -> {
            throw new SaleException("Nam is present");
        });
        sale.setIdSale(null);
        sale.setValueMin(null);
        sale.setStatus(0);
        sale.setCreateTime(new Date());
        sale.setUpdateTime(null);
        sale.setUserCreate(1); //cuongnd edit
        if (sale.getDetailSale() == null) {
            throw new StaffException("NOT NULL");
        }
        return dao.save(sale);
    }

    @Override
    public Sale update(Sale sale) {
        return null;
    }

    @Override
    public Sale delete(Integer id) {
        return null;
    }

    @Override
    public List<Sale> getall(Integer status) {
        return null;
    }

}

