package com.fix.mobile.repository;

import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
<<<<<<< Updated upstream
=======

    List<Product> findByCategoryAndStatus(Category cate, int status);
>>>>>>> Stashed changes
}