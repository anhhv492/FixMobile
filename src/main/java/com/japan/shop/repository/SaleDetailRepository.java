package com.japan.shop.repository;

import com.japan.shop.entity.SaleDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepository extends PagingAndSortingRepository<SaleDetail, Integer> {
}