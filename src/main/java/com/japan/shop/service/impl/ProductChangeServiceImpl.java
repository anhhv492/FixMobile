package com.japan.shop.service.impl;

import com.japan.shop.repository.ProductChangeRepository;
import com.japan.shop.entity.ProductChange;
import com.japan.shop.service.ProductChangeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductChangeServiceImpl implements ProductChangeService {
    private final ProductChangeRepository repository;

    public ProductChangeServiceImpl(ProductChangeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductChange save(ProductChange entity) {
        return repository.save(entity);
    }

    @Override
    public List<ProductChange> save(List<ProductChange> entities) {
        return (List<ProductChange>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ProductChange> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductChange> findAll() {
        return (List<ProductChange>) repository.findAll();
    }

    @Override
    public Page<ProductChange> findAll(Pageable pageable) {
        Page<ProductChange> entityPage = repository.findAll(pageable);
        List<ProductChange> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public ProductChange update(ProductChange entity, Integer id) {
        Optional<ProductChange> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}