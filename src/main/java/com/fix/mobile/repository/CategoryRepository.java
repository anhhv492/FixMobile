package com.fix.mobile.repository;

import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    //anhhv
    List<Category> findByType(Boolean type);

    Page<Category> findByType(boolean type,Pageable pageable);

    Page<Category> findByName(String name, Pageable pageable);
}