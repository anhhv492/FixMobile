package com.fix.mobile.service.impl;

import com.fix.mobile.entity.Product;
import com.fix.mobile.repository.ImayProductRepository;
import com.fix.mobile.entity.ImayProduct;
import com.fix.mobile.service.ImayProductService;
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

	public ImayProductServiceImpl(ImayProductRepository repository) {
		this.repository = repository;
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
		repository.deleteById(id);
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
}