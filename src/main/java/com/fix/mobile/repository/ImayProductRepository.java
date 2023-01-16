package com.fix.mobile.repository;

import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImayProductRepository extends PagingAndSortingRepository<ImayProduct, Integer> {
    List<ImayProduct> findByProduct(Product product);

    List<ImayProduct> findByProductAndStatus(Product product, int status);

    List<ImayProduct> findByOrderDetail(OrderDetail orderDetail);


    Page<ImayProduct> findAllByStatus(Pageable pageable, Integer status);

    ImayProduct findByName(String name);

    List<ImayProduct> findAllByProductAndStatus(Product product, int i);
}