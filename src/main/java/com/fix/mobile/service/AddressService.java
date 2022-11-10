package com.fix.mobile.service;


import com.fix.mobile.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    Page<AddressDTO> findAll(Pageable pageable);

    AddressDTO save(AddressDTO addressDTO);

    AddressDTO findById(Integer id);

    void delete(List<Integer> idList);

    AddressDTO update(Integer id, AddressDTO addressDTO);

    List<AddressDTO> findAll();

    List<AddressDTO> findByUsername();
}