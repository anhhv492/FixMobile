package com.example.japanshop.repository;

import com.example.japanshop.entity.ImageDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailRepository extends PagingAndSortingRepository<ImageDetail, Integer> {
}