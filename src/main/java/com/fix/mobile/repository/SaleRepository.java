package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
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
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
    Optional<Sale> findByVoucher(String voucher);

    @Query(value = "select * from sale where id_sale ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findAllSale(Pageable pageable); //đang diễn ra

    @Query(value = "select * from sale where NOW() between create_start  and create_end and quantity !='0' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findGoingOn(Pageable pageable); //đang diễn ra

    @Query(value = "select * from sale where NOW() < create_start and quantity !='0' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findUpcoming(Pageable pageable); //sắp diễn ra

    @Query(value = "select * from sale where NOW() > create_end or quantity ='0' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findStopped(Pageable pageable); //đã dừng
    @Query(value = "select * from sale where NOW() between create_start  and create_end and name like '%?1%' and quantity !='0' ORDER BY id_sale desc ",nativeQuery = true)
    Page<Sale> findGoingOnANDName(String name,Pageable pageable); //đang diễn ra + name

    @Query(value = "select * from sale where NOW() < create_start and name like '?1' and quantity !='0' ORDER BY id_sale desc ",nativeQuery = true)
    Page<Sale> findUpcomingANDName(String name,Pageable pageable); //sắp diễn ra + name

    @Query(value = "select * from sale ( where NOW() > create_end or quantity ='0' ) and name like '?1' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findStoppedANDName(String name,Pageable pageable); //đã dừng + name
    @Query(value = "select * from sale where NOW() between create_start  and create_end and voucher like '?1' and quantity !='0' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findGoingOnANDVoucher(String voucher,Pageable pageable); //đang diễn ra + voucher

    @Query(value = "select * from sale where NOW() < create_start and voucher like '?1' and quantity !='0' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findUpcomingANDVoucher(String voucher,Pageable pageable); //sắp diễn ra + voucher

    @Query(value = "select * from sale ( where NOW() > create_end or quantity ='0' ) and voucher like '?1' ORDER BY id_sale desc",nativeQuery = true)
    Page<Sale> findStoppedANDVoucher(String voucher,Pageable pageable); //đã dừng + voucher

    Page<Sale> findByNameContains(String name,Pageable pageable); // all name
    Page<Sale> findByVoucherContains(String voucher,Pageable pageable); // all voucher
    @Query(value = "select * from sale where now()>create_end",nativeQuery = true)
    List<Sale> findAllByDate();
    Sale findByIdSale(Integer id);
    @Query(value = "select id_sale from sale order by id_sale  desc limit 1",nativeQuery = true)
    Integer getLimit1Sale();

    @Query(value = "call getBigSale_UserName(?1,?2)",nativeQuery = true)
    Sale getBigSale_UserName(String username, BigDecimal money);

    @Query(value = "call getBigSale_Order(?1)",nativeQuery = true)
    Sale getBigSale_Order(BigDecimal money);

    @Query(value = "call getBigSale_ALLSHOP(?1)",nativeQuery = true)
    Sale getBigSale_ALLSHOP(BigDecimal money);

    @Query(value = "call getBigSale_Accessory(?1)",nativeQuery = true)
    Sale getBigSale_Accessory(Integer id);

    @Query(value = "call getBigSale_Products(?1)",nativeQuery = true)
    Sale getBigSale_Products(Integer id);
}