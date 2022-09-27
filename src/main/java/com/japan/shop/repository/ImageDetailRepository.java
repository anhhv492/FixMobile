package com.japan.shop.repository;

import com.japan.shop.entity.ImageDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailRepository extends PagingAndSortingRepository<ImageDetail, Integer> {
}