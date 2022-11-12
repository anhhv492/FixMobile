package com.fix.mobile.repository;

import com.fix.mobile.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
    Optional<Sale> findByVoucher(String voucher);
    @Query(value = "select * from sale s where CURDATE() between s.create_start  and s.create_end ",nativeQuery = true)
    Page<Sale> findGoingOn(Pageable pageable); //đang diễn ra

    @Query(value = "select * from sale s where CURDATE() < s.create_start ",nativeQuery = true)
    Page<Sale> findUpcoming(Pageable pageable); //sắp diễn ra

    @Query(value = "select * from sale where CURDATE() > create_end ",nativeQuery = true)
    Page<Sale> findStopped(Pageable pageable); //đã dừng
    @Query(value = "select * from sale s where CURDATE() between s.create_start  and s.create_end and s.name like '?1'",nativeQuery = true)
    Page<Sale> findGoingOnANDName(String name,Pageable pageable); //đang diễn ra + name

    @Query(value = "select * from sale s where CURDATE() < s.create_start and s.name like '?1'",nativeQuery = true)
    Page<Sale> findUpcomingANDName(String name,Pageable pageable); //sắp diễn ra + name

    @Query(value = "select * from sale s where CURDATE() > s.create_end and s.name like '?1'",nativeQuery = true)
    Page<Sale> findStoppedANDName(String name,Pageable pageable); //đã dừng + name
    @Query(value = "select * from sale s where CURDATE() between s.create_start  and s.create_end and s.voucher like '?1'",nativeQuery = true)
    Page<Sale> findGoingOnANDVoucher(String voucher,Pageable pageable); //đang diễn ra + voucher

    @Query(value = "select * from sale s where CURDATE() < s.create_start and s.voucher like '?1'",nativeQuery = true)
    Page<Sale> findUpcomingANDVoucher(String voucher,Pageable pageable); //sắp diễn ra + voucher

    @Query(value = "select * from sale s where CURDATE() > s.create_end and s.voucher like '?1'",nativeQuery = true)
    Page<Sale> findStoppedANDVoucher(String voucher,Pageable pageable); //đã dừng + voucher

    Page<Sale> findByNameContains(String name,Pageable pageable); // all name
    Page<Sale> findByVoucherContains(String voucher,Pageable pageable); // all voucher
}