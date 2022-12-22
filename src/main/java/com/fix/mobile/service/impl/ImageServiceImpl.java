package com.fix.mobile.service.impl;

import com.fix.mobile.dto.ImeiProductResponDTO;
import com.fix.mobile.service.ImageService;
import com.fix.mobile.repository.ImageRepository;
import com.fix.mobile.entity.Image;
import com.fix.mobile.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ImageServiceImpl(ImageRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Image save(Image entity) {
        return repository.save(entity);
    }

    @Override
    public List<Image> save(List<Image> entities) {
        return (List<Image>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Image> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> findAll() {
        return (List<Image>) repository.findAll();
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        Page<Image> entityPage = repository.findAll(pageable);
        List<Image> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Image update(Image entity, Integer id) {
        Optional<Image> optional = findById(id) ;
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public List<Image> findImageByPr(Integer id) {
        if(id ==null){
            return null;
        }
        return repository.findImageByPr(id);
    }
}