package com.fix.mobile.repository;

import com.fix.mobile.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    //anhhv
    List<Category> findByType(Boolean type);

    Page<Category> findByStatus(Boolean status, Pageable pageable);

    Page<Category> findByName(String name, Pageable pageable);
    Optional<Category> findByName(String name);
}