package com.fix.mobile.service.impl;

import com.fix.mobile.service.RamService;
import com.fix.mobile.repository.RamRepository;
import com.fix.mobile.entity.Ram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RamServiceImpl implements RamService {
    private final RamRepository repository;

    public RamServiceImpl(RamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ram save(Ram entity) {
        return repository.save(entity);
    }

    @Override
    public List<Ram> save(List<Ram> entities) {
        return (List<Ram>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Ram> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Ram> findAll() {
        return (List<Ram>) repository.findAll();
    }

    @Override
    public Page<Ram> findAll(Pageable pageable) {
        Page<Ram> entityPage = repository.findAll(pageable);
        List<Ram> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Ram update(Ram entity, Integer id) {
        Optional<Ram> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public Optional<Ram> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Ram> getALL() {
        return (List<Ram>) repository.findAll();
    }
}