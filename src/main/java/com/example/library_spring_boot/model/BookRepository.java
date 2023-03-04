package com.example.library_spring_boot.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    List<BookModel> findByCategory(String category);

    List<BookModel> findByAuthor(String author);
}
