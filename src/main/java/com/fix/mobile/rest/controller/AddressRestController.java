package com.fix.mobile.rest.controller;


import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.service.AddressService;
import com.fix.mobile.utils.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/user/address")
public class AddressRestController {


    public
    @Autowired
    AddressService addressService;

    @GetMapping("/page")
    public ResponseEntity<Page<AddressDTO>> page(
            @RequestParam(name = "page" , defaultValue = "1") int page,
            @RequestParam(name = "size" , defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page - 1 , size);
        return ResponseEntity.ok().body(addressService.findAll(pageable));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AddressDTO>> getAll(){
        return ResponseEntity.ok().body(addressService.findAll());
    }

    @PostMapping("/create")
    public AddressDTO create(@RequestBody AddressDTO addressDTO){
        System.out.println("username : "+ UserName.getUserName());
        return addressService.save(addressDTO);
    }

    @GetMapping("/selectOne")
    public ResponseEntity<AddressDTO> selectOne(@RequestParam("id") Integer id){
        return ResponseEntity.ok().body(addressService.findById(id));
    }

    @PostMapping("/delete")
    public void delete(@RequestBody List<Integer> idList){
        addressService.delete(idList);

    }

    @PostMapping("/update")
    public ResponseEntity<AddressDTO> update(@RequestParam("id") Integer id,
                                             @RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok().body(addressService.update(id, addressDTO));
    }


    @GetMapping("/getByUsername")
    public ResponseEntity<List<AddressDTO>> getByUsername(){
        return ResponseEntity.ok().body(addressService.findByUsername());
    }

    @GetMapping("/getProvince")
    public ResponseEntity<?> getProvince(){
        return addressService.getAllProvince();
    }

    @GetMapping("/getDistrict")
    public ResponseEntity<?> getDistrict(@RequestParam("province_id") String id){
        return addressService.getDistrict(id);
    }

    @GetMapping("/getWard")
    public ResponseEntity<?> getWard(@RequestParam("district_id") String id){
        return addressService.getWard(id);
    }

    @GetMapping("/getShipping-order")
    public ResponseEntity<?> getShippingOrder(
            @RequestParam("from_district_id") String from_district_id,
            @RequestParam("service_id") String service_id,
            @RequestParam("to_district_id") String to_district_id,
            @RequestParam("to_ward_code") String to_ward_code,
            @RequestParam("weight") String weight,
            @RequestParam("insurance_value") String insurance_value
    ){
        return ResponseEntity.ok().body(addressService.getShippingOrder(from_district_id, service_id, to_district_id,
                to_ward_code, weight, insurance_value));
    }
}
