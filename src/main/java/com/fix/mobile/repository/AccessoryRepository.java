package com.fix.mobile.repository;

import com.fix.mobile.entity.Accessory;
import com.fix.mobile.entity.Category;
import com.fix.mobile.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {
    List<Accessory> findByCategoryAndStatus(Optional<Category> cate, Boolean status);
    @Query(value = "select * from accessories where status = 1 and (name like %?1% or id_accessory like %?1%)",nativeQuery = true)
    Page<Accessory> findShowSale(String share,Pageable pageable);

    @Query(value ="select * from accessories  a  order by  a.id_accessory desc limit 4",nativeQuery = true)
    List<Accessory> findTop4();
    @Query(value = "select * from accessories where status = 1",nativeQuery = true)
    List<Accessory> findAccessory();

    @Query(value = "select MIN(price) from accessories order by price",nativeQuery = true)
    BigDecimal getMinPrice();

    @Query(value = "select MAX(price) from accessories order by price",nativeQuery = true)
    BigDecimal getMaxPrice();

//    Page<Accessory> findByStatusAndNameContaining(Boolean stt,String name, Pageable pageable);
}