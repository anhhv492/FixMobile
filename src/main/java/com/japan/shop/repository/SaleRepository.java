package com.japan.shop.repository;

import com.japan.shop.entity.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
}