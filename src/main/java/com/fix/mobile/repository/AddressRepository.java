package com.fix.mobile.repository;

import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Address;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {

    List<Address> findAll();

    List<Address> findByAccount_Username(String userName);
}