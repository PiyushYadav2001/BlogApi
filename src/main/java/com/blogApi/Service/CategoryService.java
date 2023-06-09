package com.blogApi.Service;

import com.blogApi.playload.CategoryDto;
import com.blogApi.playload.CategoryResponce;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

     void deleteCategory(Integer categoryId);

      CategoryDto getCategory(Integer categoryId);

      CategoryResponce getAllCategory(Integer pageSize,
                                      Integer pageNumber);

}
