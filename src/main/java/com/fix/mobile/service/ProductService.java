package com.fix.mobile.service;


import com.fix.mobile.dto.ColorProductResponDTO;
import com.fix.mobile.dto.ProductResponDTO;
import com.fix.mobile.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService extends GenericService<Product, Integer> {

    Page<Product> findShowSale(int pageNumber, int maxRecord, String share);
	Page<Product> getAll (Pageable page);
	Optional<Product> findByName(String name);
	List<ProductResponDTO> findByCategoryAndStatus(Integer id);

	List<Product> findByProductLimit();
	List<Product> findByProductLitmitPrice();

    List<Product> findByNameAndCapacityAndColor(String name, Integer capacity, Integer color);

    List<ColorProductResponDTO> getColorProductByName(String name);

	List<ProductResponDTO> getProductCount();

	List<ProductResponDTO> findByPriceExits();

	List<Product> findProductByPrices();

	List<Product> findProduct();

	Product getdeTailPrd(Integer idCapa,Integer idRam,Integer idColor);

	List<Integer> getlistDetailProductCapacity(Integer id);
	List<Integer> getlistDetailProductRam(Integer id);
	List<Integer> getlistDetailProductColor(Integer id);
	List<Integer> getlistDetailProductCategory();

	List<Integer> checkColor(Integer idRam,Integer idCapa);
	List<Integer> checkCapa(Integer idColor,Integer idRam);
	List<Integer> checkRam(Integer idColor,Integer idCapa);
	List<Integer> ramcheckRamAndColor(Integer idCapa);
	List<Integer> colorcheckRamAndColor(Integer idCapa);
	List<Integer> ramcheckRamAndCapa(Integer idColor);
	List<Integer> CapacheckRamAndCapa(Integer idColor);
	List<Integer> ColorcheckColorAndCapa(Integer idRam);
	List<Integer> CapacheckColorAndCapa(Integer idRam);

	List<BigDecimal> getMinMaxPrice(Integer id);
}

