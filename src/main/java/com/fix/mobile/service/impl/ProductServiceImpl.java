package com.fix.mobile.service.impl;


import com.fix.mobile.dto.ColorProductResponDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.*;
import com.fix.mobile.repository.*;
import com.fix.mobile.service.CapacityService;
import com.fix.mobile.service.ProductService;
import com.fix.mobile.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import com.fix.mobile.entity.Category;
import com.fix.mobile.service.ProductService;
import com.fix.mobile.repository.ProductRepository;
import com.fix.mobile.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Autowired
    private ImayProductRepository imayProductRepository;

    @Autowired
    private CapacityRepository capacityRepository;

    @Autowired
    private ColorRepository colorRepository;


    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product entity) {
        return repository.save(entity);
    }

    @Override
    public List<Product> save(List<Product> entities) {
        return (List<Product>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Product update(Product entity, Integer id) {
        Optional<Product> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    @Override
    public Page<Product> findShowSale(int pageNumber, int maxRecord, String share) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<Product> pageProduct = repository.findShowSale(share, pageable);
        return pageProduct;
     }
     
     @Override 
    public Page<Product> getAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public List<ProductResponDTO> findByCategoryAndStatus(Integer id) {
        int totail;
        List<Product> products= repository.getProductByCategoryIdAndStatus(id,  1);
        System.out.println(products.size());
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                ProductResponDTO productResponDTO = new ProductResponDTO();
                System.out.println(i);
                Product productImage = repository.findById(products.get(i).getIdProduct()).orElse(null);
                List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(products.get(i), 1);
                totail = imeis.size();
                productResponDTO.setIdProduct(products.get(i).getIdProduct());
                productResponDTO.setName(products.get(i).getName());
                productResponDTO.setCategory(products.get(i).getCategory());
                if(productImage != null && productImage.getImages().size() > 0){
                    productResponDTO.setImage(productImage.getImages().get(0).getName());
                } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
                productResponDTO.setPrice(products.get(i).getPrice());
                productResponDTO.setRam(products.get(i).getRam());
                productResponDTO.setCapacity(products.get(i).getCapacity());
                productResponDTO.setColor(products.get(i).getColor());
                productResponDTO.setTotail(totail);

                productResponDTOList.add(productResponDTO);
            }
            return productResponDTOList;
    }

    @Override
    public List<Product> findByProductLimit() {
        return repository.findByProductLimit();
    }

    @Override
    public List<Product> findByProductLitmitPrice() {
        return repository.findByProductLitmitPrice();
    }

    @Override
    public List<Product> findByNameAndCapacityAndColor(String name, Integer capacity, Integer color) {
        Color colorfind = colorRepository.findById(color).orElse(null);
        Capacity capacityfind = capacityRepository.findById(capacity).orElse(null);
        return repository.findByNameAndCapacityAndColor(name, capacityfind, colorfind);
    }

    @Override
    public List<ColorProductResponDTO> getColorProductByName(String name) {
        List<Product> productList = repository.findColorByNameProduct(name);
        List<ColorProductResponDTO> colorProductResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ColorProductResponDTO colorProductResponDTO = new ColorProductResponDTO();
            colorProductResponDTO.setColor(productList.get(i).getColor().getIdColor());
            colorProductResponDTOList.add(colorProductResponDTO);
        }
        return colorProductResponDTOList;
    }

    @Override
    public List<ProductResponDTO> getProductCount() {
        int totail;
        List<Product> productList = repository.findByProductLimit();
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ProductResponDTO productResponDTO = new ProductResponDTO();
            Product productImage = repository.findById(productList.get(i).getIdProduct()).orElse(null);
            List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(productList.get(i), 1);
             totail = imeis.size();
             productResponDTO.setIdProduct(productList.get(i).getIdProduct());
             productResponDTO.setName(productList.get(i).getName());
            if(productImage != null && productImage.getImages().size() > 0){
                productResponDTO.setImage(productImage.getImages().get(0).getName());
            } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
            productResponDTO.setPrice(productList.get(i).getPrice());
            productResponDTO.setTotail(totail);
            productResponDTOList.add(productResponDTO);
        }
        return productResponDTOList;
    }

    @Override
    public List<ProductResponDTO> findByPriceExits() {
        int totail;
        List<Product> productList = repository.findByProductLitmitPrice();
        List<ProductResponDTO> productResponDTOList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            ProductResponDTO productResponDTO = new ProductResponDTO();
            Product productImage = repository.findById(productList.get(i).getIdProduct()).orElse(null);
            List<ImayProduct> imeis = imayProductRepository.findByProductAndStatus(productList.get(i), 1);
            totail = imeis.size();
            productResponDTO.setIdProduct(productList.get(i).getIdProduct());
            productResponDTO.setName(productList.get(i).getName());
            if(productImage != null && productImage.getImages().size() > 0){
                productResponDTO.setImage(productImage.getImages().get(0).getName());
            } else productResponDTO.setImage("https://res.cloudinary.com/dcll6yp9s/image/upload/v1669970939/fugsyd4nw4ks0vb7kzyd.png");
            productResponDTO.setPrice(productList.get(i).getPrice());
            productResponDTO.setTotail(totail);
            productResponDTOList.add(productResponDTO);
        }
        return productResponDTOList;
    }


}
