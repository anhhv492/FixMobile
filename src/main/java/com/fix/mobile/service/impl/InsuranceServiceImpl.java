package com.fix.mobile.service.impl;

import com.fix.mobile.repository.InsuranceRepository;
import com.fix.mobile.service.InsuranceService;
import com.fix.mobile.repository.InsuranceRepository;
import com.fix.mobile.entity.Insurance;
import com.fix.mobile.service.InsuranceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService {
    private final InsuranceRepository repository;

    public InsuranceServiceImpl(InsuranceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Insurance save(Insurance entity) {
        return repository.save(entity);
    }

    @Override
    public List<Insurance> save(List<Insurance> entities) {
        return (List<Insurance>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Insurance> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Insurance> findAll() {
        return (List<Insurance>) repository.findAll();
    }

    @Override
    public Page<Insurance> findAll(Pageable pageable) {
        Page<Insurance> entityPage = repository.findAll(pageable);
        List<Insurance> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Insurance update(Insurance entity, Integer id) {
        Optional<Insurance> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}