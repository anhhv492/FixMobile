package com.fix.mobile.service;

import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.entity.Product;

import java.util.List;

public interface ImayProductService extends GenericService<ImayProduct, Integer> {
    List<ImayProduct> findByProduct(Product product);

    List<ImayProduct> findByProductAndStatus(Product product, int status);
}