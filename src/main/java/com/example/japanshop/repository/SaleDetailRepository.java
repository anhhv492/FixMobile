package com.example.japanshop.repository;

import com.example.japanshop.entity.SaleDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepository extends PagingAndSortingRepository<SaleDetail, Integer> {
}