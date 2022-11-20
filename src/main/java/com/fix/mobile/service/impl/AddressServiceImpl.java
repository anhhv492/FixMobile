package com.fix.mobile.service.impl;

import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.service.AddressService;
import com.fix.mobile.repository.AddressRepository;
import com.fix.mobile.entity.Address;

import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private String tokenApiGhn;

    private String shopId;

    @Value("${api.ghn.ShopId}")
    public void getshopId(String shopId) {
        this.shopId = shopId;
    }
    @Value("${api.ghn.token}")
    public void getTokenApiGhn(String tokenApiGhn) {
        this.tokenApiGhn = tokenApiGhn;
    }
    private HttpEntity<Object> httpEntity;

    private HttpHeaders httpHeaders;


    @PostConstruct
    public void init(){
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("token",tokenApiGhn);
        httpHeaders.add("ShopId", shopId);
        httpEntity = new HttpEntity<>(httpHeaders);
    }
    private final AddressRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccountRepository accountRepository;

    public AddressServiceImpl(AddressRepository repository, ModelMapper modelMapper, RestTemplate restTemplate, AccountRepository accountRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;

        this.accountRepository = accountRepository;
    }

    @Override
    public Page<AddressDTO> findAll(Pageable pageable) {
        Page<Address> addressPage = repository.findAll(pageable);
        Page<AddressDTO> addressDTOPage = addressPage.map(entityPage -> modelMapper.map(entityPage, AddressDTO.class));
        return addressDTOPage;
    }

    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        Account username = accountRepository.findByName(UserName.getUserName());
        System.out.println(UserName.getUserName());
        address.setAccount(username);
        Address addressSave = repository.save(address);
        AddressDTO addressDTOSave = modelMapper.map(addressSave, AddressDTO.class);

        return addressDTOSave;
    }

    @Override
    public AddressDTO findById(Integer id) {
        Address address = repository.findById(id).orElse(null);
        AddressDTO addressDTO = modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }

    @Override
    public void delete(List<Integer> idList) {
        for (Integer id : idList) {
            Address address = repository.findById(id).orElse(null);
            repository.delete(address);
        }
    }

    @Override
    public AddressDTO update(Integer id, AddressDTO addressDTO) {
        Address addressUpdate = repository.findById(id).orElse(null);
        addressUpdate = modelMapper.map(addressDTO, Address.class);
        Address addressSave = repository.save(addressUpdate);
        AddressDTO addressDTOSave = modelMapper.map(addressSave, AddressDTO.class);
        return addressDTOSave;
    }

    @Override
    public List<AddressDTO> findAll() {
        List<Address> addressList = repository.findAll();
        List<AddressDTO> addressDTOList = addressList.stream().map(add -> modelMapper.map(add, AddressDTO.class))
                .collect(Collectors.toList());
        return addressDTOList;
    }

    @Override
    public List<AddressDTO> findByUsername() {
        List<Address> addressList = repository.findByAccount_Username(UserName.getUserName());
        List<AddressDTO> addressDTOList = addressList.stream().map(address ->
                modelMapper.map(address, AddressDTO.class)).collect(Collectors.toList());
        return addressDTOList;
    }

    @Override
    public ResponseEntity<?> getAllProvince() {
        return restTemplate.exchange("https://online-gateway.ghn.vn/shiip/public-api/master-data/province",
                HttpMethod.GET, httpEntity, Object.class);
    }

    @Override
    public ResponseEntity<?> getDistrict(String id) {
        return restTemplate.exchange("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id="+id,
                HttpMethod.GET, httpEntity, Object.class);
    }

    @Override
    public ResponseEntity<?> getWard(String id) {
        return restTemplate.exchange("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id="+id,
                HttpMethod.GET, httpEntity, Object.class);
    }

    @Override
    public ResponseEntity<?> getShippingOrder(String from_district_id, String service_id
            , String to_district_id, String to_ward_code
            , String weight, String insurance_value) {
        return restTemplate.exchange("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee?from_district_id="
                        +from_district_id+"&service_id="+service_id+"&to_district_id="+to_district_id+"&to_ward_code="
                        +to_ward_code+"&weight="+weight+"&insurance_value="+insurance_value,
                HttpMethod.GET, httpEntity, Object.class);
    }


}