package com.fix.mobile.repository;

import com.fix.mobile.entity.Category;
import com.japan.shop.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
}