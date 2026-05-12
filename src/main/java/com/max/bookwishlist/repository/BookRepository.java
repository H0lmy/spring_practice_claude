package com.max.bookwishlist.repository;

import com.max.bookwishlist.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    List<Book> findByYearBetween(Integer startYear, Integer endYear);
}
