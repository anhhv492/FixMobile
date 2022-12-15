package com.fix.mobile.service.impl;

import com.fix.mobile.entity.ProductChange;
import com.fix.mobile.service.ChangeDetailService;
import com.fix.mobile.repository.ChangeDetailRepository;
import com.fix.mobile.entity.ChangeDetail;
import com.fix.mobile.service.ChangeDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChangeDetailServiceImpl implements ChangeDetailService {
    private final ChangeDetailRepository repository;

    public ChangeDetailServiceImpl(ChangeDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChangeDetail save(ChangeDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<ChangeDetail> save(List<ChangeDetail> entities) {
        return (List<ChangeDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ChangeDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ChangeDetail> findAll() {
        return (List<ChangeDetail>) repository.findAll();
    }

    @Override
    public Page<ChangeDetail> findAll(Pageable pageable) {
        Page<ChangeDetail> entityPage = repository.findAll(pageable);
        List<ChangeDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public ChangeDetail update(ChangeDetail entity, Integer id) {
        Optional<ChangeDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public void createChangeDetails(Integer id) {
        repository.createChangeDetails(id);
    }

    @Override
    public ChangeDetail findPrChangeDetails(ProductChange idPrChange) {
            return repository.findPrChangeDetails(idPrChange);
    }
}