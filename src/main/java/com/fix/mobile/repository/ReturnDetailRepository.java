package com.fix.mobile.repository;

import com.fix.mobile.entity.ReturnDetail;
import com.fix.mobile.entity.ReturnDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDetailRepository extends PagingAndSortingRepository<ReturnDetail, Integer> {
}