package com.fix.mobile.repository;

import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    Page<Product> findByStatus(Integer status, Pageable pageable);

}