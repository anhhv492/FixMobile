package com.fix.mobile.service.impl;

import com.fix.mobile.service.AddressService;
import com.japan.shop.repository.AddressRepository;
import com.japan.shop.entity.Address;
import com.japan.shop.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;

    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address entity) {
        return repository.save(entity);
    }

    @Override
    public List<Address> save(List<Address> entities) {
        return (List<Address>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Address> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Address> findAll() {
        return (List<Address>) repository.findAll();
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        Page<Address> entityPage = repository.findAll(pageable);
        List<Address> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Address update(Address entity, Integer id) {
        Optional<Address> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}