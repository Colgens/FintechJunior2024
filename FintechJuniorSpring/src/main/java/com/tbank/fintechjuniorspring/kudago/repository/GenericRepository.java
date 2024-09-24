package com.tbank.fintechjuniorspring.kudago.repository;

import java.util.List;

public interface GenericRepository<T, S> {
    List<T> findAll();
    T findById(S id);
    T save(T entity);
    void deleteById(S id);
}