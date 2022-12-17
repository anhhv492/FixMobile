package com.fix.mobile.repository;



import com.fix.mobile.entity.*;
import org.hibernate.query.NativeQuery;

import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    @Query(value = "select * from products where status = 1 and (name like %?1% or id_product like %?1%)",nativeQuery = true)
    Page<Product> findShowSale(String share,Pageable pageable);
    
    Optional<Product> findByName(String name);

    List<Product> findByCategoryAndStatus(Category category, int status);

    @Query(value ="select * from products  p where p.status = 1 order by  p.id_product desc limit 4",nativeQuery = true)
    List<Product> findByProductLimit();

    @Query(value = "select  * from  products p where p.price <= 200000 and p.status = 1 order by p.id_product desc limit 8",nativeQuery = true)
    List<Product> findByProductLitmitPrice();

    @Query(value = "select * from products p where p.name = :name ", nativeQuery = true)
    List<Product> findColorByNameProduct(String name);

    List<Product> findByNameAndCapacityAndColor(String name, Capacity capacity, Color color );

    @Query(value = "SELECT * FROM products p where p.id_category = :id and p.status = :status", nativeQuery = true)
    List<Product> getProductByCategoryIdAndStatus(Integer id, Integer status);

    @Query("SELECT p FROM Product p where p.price between 100000 and 500000")
    List<Product> findProductByPrices();
}