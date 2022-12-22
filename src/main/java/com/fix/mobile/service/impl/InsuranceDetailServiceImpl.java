package com.fix.mobile.service.impl;

import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.service.InsuranceDetailService;
import com.fix.mobile.repository.InsuranceDetailRepository;
import com.fix.mobile.entity.InsuranceDetail;
import com.fix.mobile.service.InsuranceDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsuranceDetailServiceImpl implements InsuranceDetailService {
    private final InsuranceDetailRepository repository;

    public InsuranceDetailServiceImpl(InsuranceDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public InsuranceDetail save(InsuranceDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<InsuranceDetail> save(List<InsuranceDetail> entities) {
        return (List<InsuranceDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<InsuranceDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<InsuranceDetail> findAll() {
        return (List<InsuranceDetail>) repository.findAll();
    }

    @Override
    public Page<InsuranceDetail> findAll(Pageable pageable) {
        Page<InsuranceDetail> entityPage = repository.findAll(pageable);
        List<InsuranceDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public InsuranceDetail update(InsuranceDetail entity, Integer id) {
        Optional<InsuranceDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}