package com.example.japanshop.repository;

import com.example.japanshop.entity.ProductReturn;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReturnRepository extends PagingAndSortingRepository<ProductReturn, Integer> {
}