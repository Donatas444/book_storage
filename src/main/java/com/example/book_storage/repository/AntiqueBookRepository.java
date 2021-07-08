package com.example.book_storage.repository;

import com.example.book_storage.model.AntiqueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AntiqueBookRepository extends JpaRepository<AntiqueBook, Long> {
    @Query(value = "SELECT * FROM antique_book WHERE antique_book.barcode = ?", nativeQuery = true)
    AntiqueBook findByBarcode(String barcode);
}
