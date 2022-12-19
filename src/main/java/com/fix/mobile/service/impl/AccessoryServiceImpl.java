package com.fix.mobile.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fix.mobile.dto.accessory.AccessoryDTO;
import com.fix.mobile.dto.accessory.AccessoryResponDTO;
import com.fix.mobile.entity.Category;
import com.fix.mobile.repository.AccessoryRepository;
import com.fix.mobile.repository.CategoryRepository;
import com.fix.mobile.service.AccessoryService;
import com.fix.mobile.entity.Accessory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccessoryServiceImpl implements AccessoryService {
    private final AccessoryRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloud;

    public AccessoryServiceImpl(AccessoryRepository repository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Accessory save(Accessory entity) {
        if (entity.getQuantity()<=0){
            entity.setStatus(false);
        }else{
            entity.setStatus(true);
        }
        return repository.save(entity);
    }

    @Override
    public List<Accessory> save(List<Accessory> entities) {
        return (List<Accessory>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Accessory> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Accessory> findAll() {
        return (List<Accessory>) repository.findAll();
    }

    @Override
    public Page<Accessory> findAll(Pageable pageable) {
        Page<Accessory> entityPage = repository.findAll(pageable);
        List<Accessory> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Accessory update(Accessory entity, Integer id) {
        Optional<Accessory> optional = findById(id) ;
        if (optional.isPresent()) {
            if (optional.get().getQuantity()<=0){
                entity.setStatus(false);
            }else{
                entity.setStatus(true);
            }
            return save(entity);
        }
        return null;
    }

    @Override
    public List<Accessory> findByCategoryAndStatus(Optional<Category> cate) {
        return repository.findByCategoryAndStatus(cate,true);
    }

    @Override
    public Page<Accessory> getByPage(int pageNumber, int maxRecord, String name) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<Accessory> page = repository.findShowSale(name,pageable);
        return page;
    }

    @Override
    public List<Accessory> findAccessory() {
        return null;
    }

    @Override
    public AccessoryResponDTO save(AccessoryDTO accessoryDTO) {
      try {
          Date date = new Date();
          Category category = categoryRepository.findById(accessoryDTO.getCategory()).orElse(null);
          Accessory accessory =new Accessory();
          accessory.setName(accessoryDTO.getName());
          accessory.setQuantity(accessoryDTO.getQuantity());
          accessory.setPrice(accessoryDTO.getPrice());
          accessory.setColor(accessoryDTO.getColor());
          accessory.setNote(accessoryDTO.getNote());
          accessory.setCategory(category);
          accessory.setCreateDate(date);
          if (accessoryDTO.getQuantity()<=0){
              accessory.setStatus(false);
          }else{
              accessory.setStatus(true);
          }
          if (accessoryDTO.getImage() == null) {
              Map r = this.cloud.uploader().upload(accessoryDTO.getImage().getBytes(),
                      ObjectUtils.asMap(
                              "cloud_name", "dcll6yp9s",
                              "api_key", "916219768485447",
                              "api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
                              "secure", true,
                              "folders", "c202a2cae1893315d8bccb24fd1e34b816"
                      ));
              accessory.setImage(r.get("secure_url").toString());
          }
          Accessory accessorySave =  repository.save(accessory);
          AccessoryResponDTO accessoryResponDTOSave = modelMapper.map(accessorySave, AccessoryResponDTO.class);
          accessoryResponDTOSave.setCategory(accessorySave.getCategory().getIdCategory());
          return  accessoryResponDTOSave;
      }catch (Exception e){
          System.err.println(e.getMessage());
          return null;
      }
    }

    @Override
    public AccessoryResponDTO update(Integer id, AccessoryDTO accessoryDTO) {
       try {
           Category category = categoryRepository.findById(accessoryDTO.getCategory()).orElse(null);
           Accessory optional = findById(id).orElse(null);
           if (optional != null) {
               if (optional.getQuantity()<=0){
                   optional.setStatus(false);
               }else{
                   optional.setStatus(true);
               }
               optional.setName(accessoryDTO.getName());
               optional.setQuantity(accessoryDTO.getQuantity());
               optional.setPrice(accessoryDTO.getPrice());
               optional.setColor(accessoryDTO.getColor());
               optional.setNote(accessoryDTO.getNote());
               optional.setCategory(category);
               if (accessoryDTO.getImage() == null){
                   optional.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669087979/kbasp5qdf76f3j02mebr.png");
               }else {
                   Map r = this.cloud.uploader().upload(accessoryDTO.getImage().getBytes(),
                           ObjectUtils.asMap(
                                   "cloud_name", "dcll6yp9s",
                                   "api_key", "916219768485447",
                                   "api_secret", "zUlI7pdWryWsQ66Lrc7yCZW0Xxg",
                                   "secure", true,
                                   "folders", "c202a2cae1893315d8bccb24fd1e34b816"
                           ));
                   optional.setImage(r.get("secure_url").toString());
               }
               Accessory accessorySave =  repository.save(optional);
               AccessoryResponDTO accessoryResponDTOSave = modelMapper.map(accessorySave, AccessoryResponDTO.class);
               accessoryResponDTOSave.setCategory(accessorySave.getCategory().getIdCategory());
               return  accessoryResponDTOSave;
           }
           return null;
       }catch (Exception e) {
           return null;
       }
    }

    @Override
    public List<Accessory> getTop4() {
        return repository.findTop4();
    }


}