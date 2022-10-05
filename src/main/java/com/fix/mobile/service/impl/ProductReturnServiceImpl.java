package com.fix.mobile.service.impl;

import com.fix.mobile.service.ProductReturnService;
import com.fix.mobile.repository.ProductReturnRepository;
import com.fix.mobile.entity.ProductReturn;
import com.fix.mobile.service.ProductReturnService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductReturnServiceImpl implements ProductReturnService {
    private final ProductReturnRepository repository;

    public ProductReturnServiceImpl(ProductReturnRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductReturn save(ProductReturn entity) {
        return repository.save(entity);
    }

    @Override
    public List<ProductReturn> save(List<ProductReturn> entities) {
        return (List<ProductReturn>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ProductReturn> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductReturn> findAll() {
        return (List<ProductReturn>) repository.findAll();
    }

    @Override
    public Page<ProductReturn> findAll(Pageable pageable) {
        Page<ProductReturn> entityPage = repository.findAll(pageable);
        List<ProductReturn> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public ProductReturn update(ProductReturn entity, Integer id) {
        Optional<ProductReturn> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}