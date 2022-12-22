package com.fix.mobile.service.impl;

import com.fix.mobile.dto.ImeiProductResponDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.OrderDetail;
import com.fix.mobile.entity.Product;
import com.fix.mobile.repository.ImayProductRepository;
import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.repository.ProductRepository;
import com.fix.mobile.service.ImayProductService;
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
public class ImayProductServiceImpl implements ImayProductService {
	private final ImayProductRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ImayProductServiceImpl(ImayProductRepository repository, ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ImayProduct save(ImayProduct entity) {
			return repository.save(entity);
	}

	@Override
	public List<ImayProduct> save(List<ImayProduct> entities) {
		return (List<ImayProduct>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Integer id) {
		ImayProduct imayProduct = repository.findById(id).orElse(null);
		if (imayProduct != null){
			imayProduct.setStatus(0);
			repository.save(imayProduct);
		}
	}

	@Override
	public Optional<ImayProduct> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public List<ImayProduct> findAll() {
		return (List<ImayProduct>) repository.findAll();
	}

	@Override
	public Page<ImayProduct> findAll(Pageable pageable) {
		Page<ImayProduct> entityPage = repository.findAll(pageable);
		List<ImayProduct> entities = entityPage.getContent();
		return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public ImayProduct update(ImayProduct entity, Integer id) {
		Optional<ImayProduct> optional = repository.findById(id);
		if (optional.isPresent()) {
			return save(entity);
		}
		return null;
	}


	@Override
	public List<ImayProduct> findByProduct(Product product) {
		return repository.findByProduct(product);
	}

	@Override
	public List<ImayProduct> findByProductAndStatus(Product product, int status) {
		return repository.findByProductAndStatus(product,status);
	}

	@Override
	public List<ImayProduct> findByOrderDetail(OrderDetail orderDetail) {
		return repository.findByOrderDetail(orderDetail);
	}

	@Override
	public Page<ImeiProductResponDTO> findAll(Pageable pageable, Integer status) {
		if (status != 0) {
			Page<ImayProduct> imayProductsPage = repository.findAllByStatus(pageable, status);
			Page<ImeiProductResponDTO> imeiProductResponDTOS = imayProductsPage.map(imayProduct
					-> modelMapper.map(imayProduct, ImeiProductResponDTO.class));
			return imeiProductResponDTOS;
		}else {
			Page<ImayProduct> imayProductsPages = repository.findAll(pageable);
			Page<ImeiProductResponDTO> imeiProductRespon = imayProductsPages.map(imayProduct
					-> modelMapper.map(imayProduct, ImeiProductResponDTO.class));
			return imeiProductRespon;
		}
	}

	@Override
	public List<ImayProduct> findByProductAndStatus(ProductResponDTO productResponDTO, int status) {
		Product product = productRepository.findById(productResponDTO.getIdProduct()).orElse(null);
		return repository.findByProductAndStatus(product,status);
	}

	@Override
	public ImayProduct findImeiByName(String name) {
		if (repository.findByName(name) == null){
			return null;
		}else {
			return repository.findByName(name);
		}

	}
}