package com.japan.shop.repository;

import com.japan.shop.entity.ChangeDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeDetailRepository extends PagingAndSortingRepository<ChangeDetail, Integer> {
}