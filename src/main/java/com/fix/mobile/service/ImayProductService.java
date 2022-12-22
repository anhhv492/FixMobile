package com.fix.mobile.service;

import com.fix.mobile.dto.ImeiProductResponDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImayProductService extends GenericService<ImayProduct, Integer> {
    List<ImayProduct> findByProduct(Product product);

    List<ImayProduct> findByProductAndStatus(Product product, int status);

    List<ImayProduct> findByOrderDetail(OrderDetail orderDetail);


    Page<ImeiProductResponDTO> findAll(Pageable pageable, Integer status);

    List<ImayProduct> findByProductAndStatus(ProductResponDTO productResponDTO, int status);

    ImayProduct findImeiByName(String name);
}