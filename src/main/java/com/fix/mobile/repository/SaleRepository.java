package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
    @Query(value ="select *  from sale where type_sale = 0 and discount_method = 0 and quantity != 0 and NOW() between create_start  and create_end",nativeQuery = true )
    List<Sale> getVoucherAllShop();

    @Query(value ="select *  from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale where id_product= ?1 and type_sale = 1 and discount_method = 0 and quantity != 0 and NOW() between create_start  and create_end",nativeQuery = true )
    List<Sale> getVoucherProduct(Integer id);

    @Query(value ="select *  from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale where id_accessory= ?1 and type_sale = 4 and discount_method = 0 and quantity != 0 and NOW() between create_start  and create_end",nativeQuery = true )
    List<Sale> getVoucherAccessory(Integer id);

    @Query(value ="select *  from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale where username= ?1 and user_type= 1 and type_sale = 3 and discount_method = 0 and quantity != 0 and NOW() between create_start and create_end",nativeQuery = true )
    List<Sale> getVoucherAccount(String username);

    @Query(value ="select *  from sale where user_type= 0 and type_sale = 3 and discount_method = 0 and quantity != 0 and NOW() between create_start  and create_end",nativeQuery = true )
    List<Sale> getVoucherAccountNew();

    @Query(value ="select *  from sale where value_min < ?1 and type_sale = 2 and discount_method = 0 and quantity != 0 and NOW() between create_start and create_end",nativeQuery = true )
    List<Sale> getVoucherOrder(BigDecimal money);

    @Query(value ="select id_sale from apply_sale where user_apply = ?1",nativeQuery = true )
    List<Integer> getVoucherUsed(String username);
    @Modifying @Transactional
    @Query(value = "insert into apply_sale(id_order,id_sale,user_apply) VALUE ((select id_order from orders order by id_order  desc limit 1),?1,?2 )",nativeQuery = true)
    void addSaleApply( Integer idSale, String userName);
    @Query(value = "select id_sale from apply_sale where id_order=?1",nativeQuery = true)
    Integer findSaleApply(Integer id);
}