package com.fix.mobile.rest.controller;

import com.fix.mobile.dto.CategoryDTO;
import com.fix.mobile.entity.Category;
import com.fix.mobile.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/staff/category")
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;


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

    @PutMapping("/update")
    public Category updaate(@RequestParam("id") Integer id,@RequestBody CategoryDTO categoryDTO, Category category){
        category =  categoryService.findById(id).get();
        BeanUtils.copyProperties(categoryDTO, category);
        return categoryService.save(category);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id){
        categoryService.deleteById(id);
    }

    

    @GetMapping("/page")
    public Page<Category> page (

            @RequestParam(name = "page" , defaultValue = "1") int page,
            @RequestParam(name = "size" , defaultValue = "10") int size,
            @RequestParam(name = "status", defaultValue = "1") Boolean status
    ){
        Pageable pageable = PageRequest.of(page - 1 , size);
        return categoryService.page(status, pageable);
    }


}
