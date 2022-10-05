package com.fix.mobile.service.impl;

import com.fix.mobile.service.InsuranceProductService;
import com.japan.shop.repository.InsuranceProductRepository;
import com.japan.shop.entity.InsuranceProduct;
import com.japan.shop.service.InsuranceProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsuranceProductServiceImpl implements InsuranceProductService {
    private final InsuranceProductRepository repository;

    public InsuranceProductServiceImpl(InsuranceProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public InsuranceProduct save(InsuranceProduct entity) {
        return repository.save(entity);
    }

    @Override
    public List<InsuranceProduct> save(List<InsuranceProduct> entities) {
        return (List<InsuranceProduct>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<InsuranceProduct> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<InsuranceProduct> findAll() {
        return (List<InsuranceProduct>) repository.findAll();
    }

    @Override
    public Page<InsuranceProduct> findAll(Pageable pageable) {
        Page<InsuranceProduct> entityPage = repository.findAll(pageable);
        List<InsuranceProduct> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public InsuranceProduct update(InsuranceProduct entity, Integer id) {
        Optional<InsuranceProduct> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}