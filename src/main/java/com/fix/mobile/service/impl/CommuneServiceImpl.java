package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Commune;
import com.fix.mobile.service.CommuneService;
import com.fix.mobile.repository.CommuneRepository;
import com.fix.mobile.entity.Commune;
import com.fix.mobile.service.CommuneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommuneServiceImpl implements CommuneService {
    private final CommuneRepository repository;

    public CommuneServiceImpl(CommuneRepository repository) {
        this.repository = repository;
    }

    @Override
    public Commune save(Commune entity) {
        return repository.save(entity);
    }

    @Override
    public List<Commune> save(List<Commune> entities) {
        return (List<Commune>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Commune> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Commune> findAll() {
        return (List<Commune>) repository.findAll();
    }

    @Override
    public Page<Commune> findAll(Pageable pageable) {
        Page<Commune> entityPage = repository.findAll(pageable);
        List<Commune> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Commune update(Commune entity, Integer id) {
        Optional<Commune> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}