package com.japan.shop.service.impl;

import com.japan.shop.repository.DistrictRepository;
import com.japan.shop.entity.District;
import com.japan.shop.service.DistrictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository repository;

    public DistrictServiceImpl(DistrictRepository repository) {
        this.repository = repository;
    }

    @Override
    public District save(District entity) {
        return repository.save(entity);
    }

    @Override
    public List<District> save(List<District> entities) {
        return (List<District>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<District> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<District> findAll() {
        return (List<District>) repository.findAll();
    }

    @Override
    public Page<District> findAll(Pageable pageable) {
        Page<District> entityPage = repository.findAll(pageable);
        List<District> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public District update(District entity, Integer id) {
        Optional<District> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}