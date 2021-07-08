package com.example.book_storage.repository;

import com.example.book_storage.model.ScienceJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceJournalRepository extends JpaRepository<ScienceJournal, Long> {
    @Query(value = "SELECT * FROM science_journal WHERE science_journal.barcode = ?", nativeQuery = true)
    ScienceJournal findByBarcode(String barcode);
}
