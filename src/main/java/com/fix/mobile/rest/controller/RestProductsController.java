package com.fix.mobile.rest.controller;

import com.fix.mobile.entity.*;
import com.fix.mobile.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value= "/rest/admin/product")
public class RestProductsController {
	@Autowired
	private ProductService productService;

	@Autowired
	private RamService ramService;
	@Autowired
	private ColorService colorService;

	@Autowired
	private CapacityService capacityService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/getAllRam")
	public List<Ram> findAllRam(){
		return ramService.findAll();
	}

	@GetMapping("/getAllCapacity")
	public List<Capacity> findAllCapacity(){
		return capacityService.findAll();
	}
	@GetMapping("/getAllColor")
	public List<Color> findAllColor(){
		return colorService.findAll();
	}
	// ảnh
	@GetMapping("/getAllImage")
	public List<Image> findAllImage(){
		return imageService.findAll();
	}
	// danh mục
	@GetMapping("/category")
	public List<Category> findByCate(){
		return categoryService.findByTypeProduct();
	}

	@GetMapping("/getAll")
	public List<Product> findAll(){
		return productService.findAll();
	}


	public List<Product> findAllPageable(@PathVariable("page") Optional<Integer> page){
		Pageable pageable = PageRequest.of(page.get(), 5);
		List<Product> products = productService.findAll(pageable).getContent();
		return products;
	}

	@PostMapping("/saveProduct")
	public Product save(@RequestBody Product products){
		return productService.save(products);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id){
		productService.deleteById(id);
	}

	@PutMapping("/{id}")
	public Product update(@PathVariable("id") Integer id, @RequestBody Product product){
		return productService.update(product,id);
	}
	@GetMapping(value="/page/{page}")
	public Page<Product> findAllPage(@PathVariable("page") Integer page){
		Page<Product> pageProduct = productService
				.getByPage(page, 5,0);
		return pageProduct;
	}
}
