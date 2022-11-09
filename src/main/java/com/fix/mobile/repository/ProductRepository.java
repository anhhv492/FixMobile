package com.fix.mobile.repository;


import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Page<Product> findByStatus(Integer status, Pageable pageable);
    
    Optional<Product> findByName(String name);

    List<Product> findByCategoryAndStatus(Category cate, int status);

    @Query(value ="select * from products  p  order by  p.id_product desc limit 4",nativeQuery = true)
    List<Product> findByProductLimit();

    @Query(value = "select  * from  products p where p.price <= 200000 order by p.id_product desc limit 6 ",nativeQuery = true)
    List<Product> findByProductLitmitPrice();



}