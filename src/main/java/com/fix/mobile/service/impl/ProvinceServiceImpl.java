package com.fix.mobile.service.impl;

import com.fix.mobile.service.ProvinceService;
import com.fix.mobile.repository.ProvinceRepository;
import com.fix.mobile.entity.Province;
import com.fix.mobile.service.ProvinceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository repository;

    public ProvinceServiceImpl(ProvinceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Province save(Province entity) {
        return repository.save(entity);
    }

    @Override
    public List<Province> save(List<Province> entities) {
        return (List<Province>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Province> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Province> findAll() {
        return (List<Province>) repository.findAll();
    }

    @Override
    public Page<Province> findAll(Pageable pageable) {
        Page<Province> entityPage = repository.findAll(pageable);
        List<Province> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Province update(Province entity, Integer id) {
        Optional<Province> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}