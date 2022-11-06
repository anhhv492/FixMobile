package com.fix.mobile.repository;

import com.fix.mobile.entity.ImayProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImayProductRepository extends PagingAndSortingRepository<ImayProduct, Integer> {

}