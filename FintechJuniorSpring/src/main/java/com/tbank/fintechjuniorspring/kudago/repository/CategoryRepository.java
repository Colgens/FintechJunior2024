package com.tbank.fintechjuniorspring.kudago.repository;

import com.tbank.fintechjuniorspring.kudago.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CategoryRepository implements GenericRepository<Category, Long> {
    private final ConcurrentHashMap<Long, Category> categories = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Category findById(Long id) {
        return categories.get(id);
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(idGenerator.getAndIncrement());
        }
        categories.put(category.getId(), category);
        return category;
    }

    @Override
    public void deleteById(Long id) {
        categories.remove(id);
    }
}