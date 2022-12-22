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
    public Category create(@RequestBody Category category){
        Category categorySave = categoryService.save(category);
        if (categorySave != null) return categorySave;
        throw new RuntimeException();
    }

    @PutMapping("/update")
    public Category updaate(@RequestParam("id") Integer id,@RequestBody Category category){
        return categoryService.update(category, id);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Integer id){
        categoryService.deleteById(id);
    }

    

    @GetMapping("/page")
    public Page<Category> page (
            @RequestParam(name = "page" , defaultValue = "1") int page,
            @RequestParam(name = "size" , defaultValue = "10") int size,
            @RequestParam(name = "status", defaultValue = "1") Integer status,
            @RequestParam(name = "type", defaultValue = "-1") Integer type,
            @RequestParam(name = "name", required = false) String name
    ){
        Pageable pageable = PageRequest.of(page - 1 , size);
        return categoryService.page(name, type, status, pageable);
    }


}
