package com.fix.mobile.service.impl;

import com.fix.mobile.dto.AddressDTO;
import com.fix.mobile.entity.Account;
import com.fix.mobile.repository.AccountRepository;
import com.fix.mobile.service.AddressService;
import com.fix.mobile.repository.AddressRepository;
import com.fix.mobile.entity.Address;
import com.fix.mobile.service.AddressService;
import com.fix.mobile.utils.UserName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

   @Autowired
    ModelMapper modelMapper;

   @Autowired
    AccountRepository accountRepository;

    public AddressServiceImpl(AddressRepository repository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
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
}