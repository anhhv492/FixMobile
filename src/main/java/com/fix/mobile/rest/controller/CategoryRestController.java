package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.CategoryDTO;
import com.fix.mobile.entity.Category;
import com.fix.mobile.repository.CategoryRepository;
import com.fix.mobile.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/staff/category")
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/getAll")
    public List<Category> getAll(){
        return categoryService.findAll();
    }

    @GetMapping("/getOne/{id}")
    public Category getOne(@PathVariable("id") Integer id){
        return categoryService.findById(id).get();
    }

    @PostMapping("/create")
    public Category create(@RequestBody CategoryDTO categoryDTO, Category category){
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryService.save(category);
    }

    @PutMapping("/update/{id}")
    public Category updaate(@PathVariable("id") Integer id,@RequestBody CategoryDTO categoryDTO, Category category){
        category =  categoryService.findById(id).get();
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryService.save(category);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        categoryService.deleteById(id);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
    

    @GetMapping("/page/published")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            List<Category> categories = new ArrayList<Category>();
            Pageable paging = PageRequest.of(page, size);

            Page<Category> pageTuts = categoryRepository.findAll(paging);
            categories = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
