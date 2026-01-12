package com.BillingSoftware.Server.controller;

import com.BillingSoftware.Server.io.categoryRequest;
import com.BillingSoftware.Server.io.categoryResponse;
import com.BillingSoftware.Server.service.categoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@RequestMapping("/categories")

public class categoryController {

    //injecting the service layers dependency
    private final categoryService categoryService;

    public categoryController(com.BillingSoftware.Server.service.categoryService categoryService) {
        this.categoryService = categoryService;
    }

    //method to add the categories
    @PostMapping("/admin/categories")
    public categoryResponse addCategory(@RequestBody categoryRequest categoryRequest){
         categoryResponse response = categoryService.addCategory(categoryRequest);
         return response;
    }

    //method to return all the categories
    @GetMapping("/categories")
    public List<categoryResponse> readAll(){
        return categoryService.readAll();
    }

    //method to delete the category by id
    @DeleteMapping("/admin/categories/{CategoryId}")
    public void deleteCategory(@PathVariable String CategoryId){
       try {
           categoryService.deleteById(CategoryId);
       }catch (Exception e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
       }
    }
}
