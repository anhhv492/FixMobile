package com.fix.mobile.service.impl;

import com.fix.mobile.entity.ReturnDetail;
import com.fix.mobile.service.ReturnDetailService;
import com.japan.shop.repository.ReturnDetailRepository;
import com.japan.shop.entity.ReturnDetail;
import com.japan.shop.service.ReturnDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReturnDetailServiceImpl implements ReturnDetailService {
    private final ReturnDetailRepository repository;

    public ReturnDetailServiceImpl(ReturnDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReturnDetail save(ReturnDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<ReturnDetail> save(List<ReturnDetail> entities) {
        return (List<ReturnDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ReturnDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ReturnDetail> findAll() {
        return (List<ReturnDetail>) repository.findAll();
    }

    @Override
    public Page<ReturnDetail> findAll(Pageable pageable) {
        Page<ReturnDetail> entityPage = repository.findAll(pageable);
        List<ReturnDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public ReturnDetail update(ReturnDetail entity, Integer id) {
        Optional<ReturnDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}