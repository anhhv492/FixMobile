package com.fix.mobile.service.impl;

import com.fix.mobile.service.SaleService;
import com.fix.mobile.repository.SaleRepository;
import com.fix.mobile.entity.Sale;
import com.fix.mobile.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final SaleRepository repository;

    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale save(Sale entity) {
        return repository.save(entity);
    }

    @Override
    public List<Sale> save(List<Sale> entities) {
        return (List<Sale>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Sale> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Sale> findAll() {
        return (List<Sale>) repository.findAll();
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        Page<Sale> entityPage = repository.findAll(pageable);
        List<Sale> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Sale update(Sale entity, Integer id) {
        Optional<Sale> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}