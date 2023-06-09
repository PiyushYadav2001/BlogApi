package com.blogApi.Service.impl;

import com.blogApi.Exception.ResourceNotFoundException;
import com.blogApi.Repo.CategoryRepo;
import com.blogApi.Service.CategoryService;
import com.blogApi.entity.Category;
import com.blogApi.playload.CategoryDto;
import com.blogApi.playload.CategoryResponce;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceimpl implements CategoryService {
      @Autowired
      private CategoryRepo categoryRepo;
      @Autowired
      private ModelMapper modelMapper;
      @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
          Category map = this.modelMapper.map(categoryDto, Category.class);
          Category save = this.categoryRepo.save(map);
          return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
          Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId));
          cat.setCategoryTitle(categoryDto.getCategoryTitle());
          cat.setCategoryDescripton(categoryDto.getCategoryDescripton());
        Category updatesave = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatesave,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
            Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId));
            this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryResponce getAllCategory(Integer pageNumber,Integer pageSize) {
        PageRequest of = PageRequest.of(pageNumber, pageSize);
        Page<Category> alls = this.categoryRepo.findAll(of);
        List<Category> all=alls.getContent();
        List<CategoryDto> collect = all.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
            CategoryResponce categoryResponce=new CategoryResponce();
            categoryResponce.setContent(collect);
            categoryResponce.setPageNumber(alls.getNumber());
            categoryResponce.setPageSize(alls.getSize());
            categoryResponce.setTotalElement(alls.getTotalElements());
            categoryResponce.setTotalPages(alls.getTotalPages());
            categoryResponce.setLastPage(alls.isLast());
        return categoryResponce;
    }
}
