package com.fix.mobile.service;

import com.fix.mobile.entity.Category;

import java.util.List;

public interface CategoryService extends GenericService<Category, Integer> {
    //anhhv findcategory
    List<Category> findByType();
}