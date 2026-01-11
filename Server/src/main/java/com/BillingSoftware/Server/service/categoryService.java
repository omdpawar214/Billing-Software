package com.BillingSoftware.Server.service;

import com.BillingSoftware.Server.entity.categoryEntity;
import com.BillingSoftware.Server.io.categoryRequest;
import com.BillingSoftware.Server.io.categoryResponse;
import com.BillingSoftware.Server.repositery.categoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class categoryService {

    //injecting the dependency of the repository
    private final categoryRepository categoryRepository;
    public categoryService(com.BillingSoftware.Server.repositery.categoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    //method to read all the categories
    public List<categoryResponse> readAll() {
        List<categoryEntity> categories = categoryRepository.findAll();
        return convertToList(categories);
    }

    //METHOD TO CONVERT Entity OBJECTS INTO RESPONSE OBJECT
    private List<categoryResponse> convertToList(List<categoryEntity> categories) {
       List<categoryResponse> list = new ArrayList<>();
       //converting all the entity objects into the response objects
        for (int i=0;i<categories.size();i++){
            list.add(convertToResponse(categories.get(i)));        }
        return list;
    }

    //method to store the category into the database
    public categoryResponse addCategory(categoryRequest request){

        categoryEntity category = convertToEntity(request);

        category=  categoryRepository.save(category);

        categoryResponse response = convertToResponse(category);
       return response;
    }

    //METHOD TO CONVERT Entity OBJECT INTO RESPONSE OBJECT
    private categoryResponse convertToResponse(categoryEntity category) {
        return categoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    //METHOD TO CONVERT REQUEST OBJECT INTO ENTITY OBJECT
    private categoryEntity convertToEntity(categoryRequest request) {
        return categoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }

    //method to delete the category
    public void deleteById(String CategoryId) {
        categoryEntity existingCategory = categoryRepository.findByCategoryId( CategoryId)
                .orElseThrow(()-> new RuntimeException("Category not Found" + CategoryId));

        categoryRepository.delete(existingCategory);
    }


}
