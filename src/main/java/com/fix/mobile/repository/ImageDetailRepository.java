package com.fix.mobile.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailRepository extends PagingAndSortingRepository<ImageDetail, Integer> {
}