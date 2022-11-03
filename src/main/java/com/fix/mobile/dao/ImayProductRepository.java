package com.fix.mobile.dao;

import com.fix.mobile.entity.ImayProduct;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImayProductRepository extends PagingAndSortingRepository<ImayProduct, Integer> {
}