package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
import com.tbank.fintechjuniorspring.kudago.model.Category;
import com.tbank.fintechjuniorspring.kudago.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    public void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");
    }

    @Test
    public void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(2, categories.size());
        assertEquals("Category 1", categories.get(0).getName());
        assertEquals("Category 2", categories.get(1).getName());
    }

    @Test
    public void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(category1);

        Category category = categoryService.getCategoryById(1L);

        assertNotNull(category);
        assertEquals("Category 1", category.getName());
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    public void testCreateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(null);
        when(categoryRepository.save(category1)).thenReturn(category1);

        Category createdCategory = categoryService.createCategory(category1);

        assertNotNull(createdCategory);
        assertEquals("Category 1", createdCategory.getName());
    }

    @Test
    public void testCreateCategoryAlreadyExists() {
        when(categoryRepository.findById(1L)).thenReturn(category1);

        assertThrows(IllegalArgumentException.class, () -> categoryService.createCategory(category1));
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(category1);
        when(categoryRepository.save(category1)).thenReturn(category1);

        Category updatedCategory = categoryService.updateCategory(1L, category1);

        assertNotNull(updatedCategory);
        assertEquals("Category 1", updatedCategory.getName());
    }

    @Test
    public void testUpdateCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(1L, category1));
    }

    @Test
    public void testDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(category1);

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}