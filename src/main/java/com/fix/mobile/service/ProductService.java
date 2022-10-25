package com.fix.mobile.service;


import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends GenericService<Product, Integer> {

    Page<Product> getByPage(int pageNumber, int maxRecord, Integer status);
}