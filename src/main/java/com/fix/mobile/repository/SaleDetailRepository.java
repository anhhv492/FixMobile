package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import com.fix.mobile.entity.SaleDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SaleDetailRepository extends PagingAndSortingRepository<SaleDetail, Integer> {
    @Modifying
    @Query(value = "insert into sale_detail(id_sale,id_product) values((select id_sale from sale order by id_sale  desc limit 1),?1)",nativeQuery = true)
    void creatSaleDetailProduct(String idthem);
    @Modifying
    @Query(value = "insert into sale_detail(id_sale,id_accessory) values((select id_sale from sale order by id_sale  desc limit 1),?1)",nativeQuery = true)
    void creatSaleDetailAccessory(String idthem);
    @Modifying
    @Query(value = "insert into sale_detail(id_sale,username) values((select id_sale from sale order by id_sale  desc limit 1),'?1')",nativeQuery = true)
    void creatSaleDetailusername(String idthem);

    List<SaleDetail> findBySale(Sale sale);

}