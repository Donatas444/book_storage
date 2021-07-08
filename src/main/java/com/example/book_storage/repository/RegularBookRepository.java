package com.example.book_storage.repository;

import com.example.book_storage.model.RegularBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularBookRepository extends JpaRepository<RegularBook, Long> {
    @Query(value = "SELECT * FROM regular_book WHERE regular_book.barcode = ?", nativeQuery = true)
    RegularBook findByBarcode(String barcode);
}
