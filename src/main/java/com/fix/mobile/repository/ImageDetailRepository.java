package com.fix.mobile.repository;

import com.fix.mobile.entity.ImageDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailRepository extends PagingAndSortingRepository<ImageDetail, Integer> {
}