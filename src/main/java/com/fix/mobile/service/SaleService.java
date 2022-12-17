package com.fix.mobile.service;


import com.fix.mobile.entity.Sale;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface SaleService {
    public Sale add(Sale sale);

    public Sale update(Sale sale);

    public Sale updateQuantity(Sale sale);

    public void delete(Integer id);

    public Integer findSaleApply(Integer id);

    public List<Sale> getall(Integer status);

    public Sale findByid(Integer id);

    public Integer getLimit1Sale();

    public Sale getBigSale(String userName, BigDecimal money,Integer idPrd,Integer idAcsr);
    public Sale getBigSale_Order(BigDecimal money);

    public void addApply_Sale(Integer idSale, String userName);

    public List<Sale> getSaleByVoucher(String userName, BigDecimal money,List<Integer> idPrd,List<Integer> idAcsr);

    Page<Sale> getByPage(int pageNumber, int maxRecord, Integer Status, String share,String type);
}