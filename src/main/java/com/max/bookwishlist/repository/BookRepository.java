package com.max.bookwishlist.repository;

import com.max.bookwishlist.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}
