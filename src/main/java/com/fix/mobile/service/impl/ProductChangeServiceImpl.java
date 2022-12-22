package com.fix.mobile.service.impl;

import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.service.ProductChangeService;
import com.fix.mobile.repository.ProductChangeRepository;
import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ProductChangeService;
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

    @Override
    public List<ProductChange> listProductChange() {
        return repository.findProductChange();
    }

    @Override
    public List<ProductChange> findAllProductChange() {
        return repository.findAllProductChange();
    }

    @Override
    public ProductChange findByStatus(Integer idPrChange) {
        if(idPrChange != null){
            return repository.findByStatus(idPrChange);
        }return null;
    }

    @Override
    public List<ProductChange> findByUsername(String username) {
        if(username != null){
            return repository.findByAccount(username);
        }
        return null;
    }

    @Override
    public List<ProductChange> findByStatusSendEmail(Integer idChange) {
        return repository.findByStatusSendEmail(idChange);
    }


}