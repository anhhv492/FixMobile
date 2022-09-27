package com.japan.shop.repository;

import com.japan.shop.entity.ReturnDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDetailRepository extends PagingAndSortingRepository<ReturnDetail, Integer> {
}