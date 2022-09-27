package com.example.japanshop.repository;

import com.example.japanshop.entity.ProductChange;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductChangeRepository extends PagingAndSortingRepository<ProductChange, Integer> {
}