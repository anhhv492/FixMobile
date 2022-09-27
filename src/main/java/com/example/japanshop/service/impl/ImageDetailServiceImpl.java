package com.example.japanshop.service.impl;

import com.example.japanshop.repository.ImageDetailRepository;
import com.example.japanshop.entity.ImageDetail;
import com.example.japanshop.service.ImageDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageDetailServiceImpl implements ImageDetailService {
    private final ImageDetailRepository repository;

    public ImageDetailServiceImpl(ImageDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImageDetail save(ImageDetail entity) {
        return repository.save(entity);
    }

    @Override
    public List<ImageDetail> save(List<ImageDetail> entities) {
        return (List<ImageDetail>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ImageDetail> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ImageDetail> findAll() {
        return (List<ImageDetail>) repository.findAll();
    }

    @Override
    public Page<ImageDetail> findAll(Pageable pageable) {
        Page<ImageDetail> entityPage = repository.findAll(pageable);
        List<ImageDetail> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public ImageDetail update(ImageDetail entity, Integer id) {
        Optional<ImageDetail> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}