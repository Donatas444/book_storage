package com.example.book_storage.service;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.model.RegularBook;
import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.repository.AntiqueBookRepository;
import com.example.book_storage.repository.RegularBookRepository;
import com.example.book_storage.repository.ScienceJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllBooksService {
    @Autowired
    AntiqueBookRepository antiqueBookRepository;
    @Autowired
    RegularBookRepository regularBookRepository;
    @Autowired
    ScienceJournalRepository scienceJournalRepository;

    public List<Object> allBooksByQuantity() {
        List<Object> allBooks = new ArrayList<>();
        List<RegularBook> regularBookList = regularBookRepository.findAll();
        List<ScienceJournal> scienceJournalList = scienceJournalRepository.findAll();
        List<AntiqueBook> antiqueBookList = antiqueBookRepository.findAll();
        allBooks.addAll(regularBookList);
        allBooks.addAll(scienceJournalList);
        allBooks.addAll(antiqueBookList);
        return allBooks;
    }
}