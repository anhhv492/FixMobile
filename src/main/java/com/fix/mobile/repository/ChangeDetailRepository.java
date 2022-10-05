package com.fix.mobile.repository;

import com.fix.mobile.entity.ChangeDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeDetailRepository extends PagingAndSortingRepository<ChangeDetail, Integer> {
}