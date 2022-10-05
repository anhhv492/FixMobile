package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
}