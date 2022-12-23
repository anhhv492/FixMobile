package com.fix.mobile.repository;



import com.fix.mobile.entity.*;
import org.hibernate.query.NativeQuery;

import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
    @Query(value = "select * from products where status = 1",nativeQuery = true)
    List<Product> findProduct();
    @Query(value = "select MIN(price) from products where id_category = ?1 and status=1 order by price",nativeQuery = true)
    BigDecimal getMinPrice(Integer id);

    @Query(value = "select MAX(price) from products where id_category = ?1 and status=1 order by price",nativeQuery = true)
    BigDecimal getMaxPrice(Integer id);
    @Query(value = "select distinct id_color from products where id_category=?1 and status=1",nativeQuery = true)
    List<Integer> getlistDetailProductColor(Integer id);
    @Query(value = "select distinct id_ram from products where id_category = ?1 and status=1",nativeQuery = true)
    List<Integer> getlistDetailProductRam(Integer id);
    @Query(value = "select distinct id_capacity from products where id_category =?1 and status=1",nativeQuery = true)
    List<Integer> getlistDetailProductCapacity(Integer id);
    @Query(value = "select distinct id_category from products where status = 1",nativeQuery = true)
    List<Integer> getlistDetailProductCategory();
    @Query(value = "select * from products where id_capacity =?1 and id_ram  =?2 and id_color=?3 and status=1 limit 1",nativeQuery = true)
    Product getDetailPrd(Integer idCapa, Integer idRam, Integer idColor);
    @Query(value = "SELECT products.id_product  FROM products where id_category = ?1 and status = 1", nativeQuery = true)
    List<Integer> getIdimage(Integer id);

    @Query(value = "select name from images where id_product =?1 limit 1",nativeQuery = true)
    String getImg(Integer id);
    @Modifying
    @Query(value = "delete from order_detail where id_order =?1",nativeQuery = true)
    void deleteIFsaleEnd(Integer id);
}