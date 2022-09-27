package com.example.japanshop.service.impl;

import com.example.japanshop.repository.ImeiRepository;
import com.example.japanshop.entity.Imei;
import com.example.japanshop.service.ImeiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImeiServiceImpl implements ImeiService {
    private final ImeiRepository repository;

    public ImeiServiceImpl(ImeiRepository repository) {
        this.repository = repository;
    }

    @Override
    public Imei save(Imei entity) {
        return repository.save(entity);
    }

    @Override
    public List<Imei> save(List<Imei> entities) {
        return (List<Imei>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Imei> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Imei> findAll() {
        return (List<Imei>) repository.findAll();
    }

    @Override
    public Page<Imei> findAll(Pageable pageable) {
        Page<Imei> entityPage = repository.findAll(pageable);
        List<Imei> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Imei update(Imei entity, Integer id) {
        Optional<Imei> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}