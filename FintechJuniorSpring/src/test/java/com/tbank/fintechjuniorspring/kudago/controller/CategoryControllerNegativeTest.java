package com.tbank.fintechjuniorspring.kudago.controller;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
import com.tbank.fintechjuniorspring.kudago.exception.RestExceptionHandler;
import com.tbank.fintechjuniorspring.kudago.model.Category;
import com.tbank.fintechjuniorspring.kudago.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerNegativeTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestExceptionHandler()) // Добавляем обработчик исключений
                .build();
    }

    @Test
    public void testGetCategoryByIdNotFound() throws Exception {
        when(categoryService.getCategoryById(1L)).thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(get("/api/v1/places/categories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCategoryAlreadyExists() throws Exception {
        when(categoryService.createCategory(any(Category.class))).thenThrow(new IllegalArgumentException("Category already exists"));

        mockMvc.perform(post("/api/v1/places/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"slug\":\"slug1\",\"name\":\"Category 1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategoryNotFound() throws Exception {
        when(categoryService.updateCategory(eq(1L), any(Category.class))).thenThrow(new ResourceNotFoundException("Category not found"));

        mockMvc.perform(put("/api/v1/places/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"slug\":\"slug1\",\"name\":\"Category 1\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCategoryNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Category not found")).when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/v1/places/categories/1"))
                .andExpect(status().isNotFound());
    }
}