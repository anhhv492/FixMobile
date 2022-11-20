package com.fix.mobile.service;


import com.fix.mobile.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    Page<AddressDTO> findAll(Pageable pageable);

    AddressDTO save(AddressDTO addressDTO);

    AddressDTO findById(Integer id);

    void delete(List<Integer> idList);

    AddressDTO update(Integer id, AddressDTO addressDTO);

    List<AddressDTO> findAll();

    List<AddressDTO> findByUsername();

    ResponseEntity<?> getAllProvince();

    ResponseEntity<?> getDistrict(String id);

    ResponseEntity<?> getWard(String id);



    ResponseEntity<?> getShippingOrder(String from_district_id, String service_id, String to_district_id,
                                       String to_ward_code, String weight, String insurance_value);
}