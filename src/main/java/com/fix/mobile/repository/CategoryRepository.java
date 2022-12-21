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
    List<Category> findByType(Integer type);

    Page<Category> findByStatus(Integer status, Pageable pageable);
    Page<Category> findByType(Integer type, Pageable pageable);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Page<Category> findByNameContainingAndStatus(String name,Integer status, Pageable pageable);

    Page<Category> findByNameContainingAndType(String name,Integer type, Pageable pageable);

    Page<Category> findByNameContainingAndTypeAndStatus(String name,Integer type, Integer status, Pageable pageable);

    Page<Category> findByTypeAndStatus(Integer type, Integer status, Pageable pageable);
    List<Category> findByTypeAndStatus(Integer type, Integer status);
    Page<Category> findByName(String name, Pageable pageable);
    Optional<Category> findByName(String name);

    Optional<Category> getCategoryByTypeAndName(Integer type, String name);

    List<Category> findAllByStatus(Integer status);
}