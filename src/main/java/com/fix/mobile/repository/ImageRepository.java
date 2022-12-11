package com.fix.mobile.repository;

import com.fix.mobile.entity.Image;
import com.fix.mobile.entity.ImayProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<Image, Integer> {


}