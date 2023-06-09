package com.blogApi.Controller;

import com.blogApi.Service.CategoryService;
import com.blogApi.playload.CategoryDto;

import com.blogApi.playload.CategoryResponce;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody  CategoryDto categoryDto){
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updatecategory( @Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer uId) {
       CategoryDto Updatedcategory = this.categoryService.updateCategory(categoryDto, uId);
        return  ResponseEntity.ok(Updatedcategory);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deletecategory(@PathVariable("categoryId") Integer uid){
        this.categoryService.deleteCategory(uid);
        return new  ResponseEntity(Map.of("message","Category Deleted Sucesddfully"),HttpStatus.OK);
    }

    @GetMapping("/")
    public  ResponseEntity<CategoryResponce> getAllcategorys(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
                                                                  @RequestParam(value = "pageSize",defaultValue = "0",required = false)Integer pageSize) {
        CategoryResponce allCategory = this.categoryService.getAllCategory(pageSize, pageNumber);
        return new ResponseEntity<CategoryResponce>(allCategory,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSinglecategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
    }
}
