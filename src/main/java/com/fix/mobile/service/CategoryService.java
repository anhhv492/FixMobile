package com.fix.mobile.service;

import com.fix.mobile.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService extends GenericService<Category, Integer> {
    //anhhv findcategory
    List<Category> findByType();

    //anhhv findcategory
    Optional<Category> findByName(String name);
    
    List<Category> findByTypeProduct();

    List<Category> findByTypeSP();

    Page<Category> page(String name, Integer type, Integer status, Pageable pageable);

    void deleteByListId(List<Integer> idList);

    List<Category> findAllBybStatus();
}