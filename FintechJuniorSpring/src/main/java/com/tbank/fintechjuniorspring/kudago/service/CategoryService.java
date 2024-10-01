package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
import com.tbank.fintechjuniorspring.kudago.model.Category;
import com.tbank.fintechjuniorspring.kudago.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        return category;
    }

    public Category createCategory(Category category) {
        if (category.getId() == null) {
            long id = idGenerator.getAndIncrement();
            while (categoryRepository.findById(id) != null) {
                id = idGenerator.getAndIncrement();
            }
            category.setId(id);
        } else if (categoryRepository.findById(category.getId()) != null) {
            throw new IllegalArgumentException("Category with id " + category.getId() + " already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        if (categoryRepository.findById(id) == null) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        if (category.getId() != null && !category.getId().equals(id)) {
            throw new IllegalArgumentException("IDs in the URL and JSON body of the request do not match");
        }
        category.setId(id);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.findById(id) == null) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

}
