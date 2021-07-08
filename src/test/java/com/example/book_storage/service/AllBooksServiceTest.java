package com.example.book_storage.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.model.RegularBook;
import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.repository.AntiqueBookRepository;
import com.example.book_storage.repository.RegularBookRepository;
import com.example.book_storage.repository.ScienceJournalRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AllBooksService.class})
@ExtendWith(SpringExtension.class)
public class AllBooksServiceTest {
    @Autowired
    private AllBooksService allBooksService;

    @MockBean
    private AntiqueBookRepository antiqueBookRepository;

    @MockBean
    private RegularBookRepository regularBookRepository;

    @MockBean
    private ScienceJournalRepository scienceJournalRepository;

    @Test
    public void testAllBooksByQuantity() {
        when(this.scienceJournalRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.regularBookRepository.findAll()).thenReturn(new ArrayList<>());
        when(this.antiqueBookRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.allBooksService.allBooksByQuantity().isEmpty());
        verify(this.scienceJournalRepository).findAll();
        verify(this.regularBookRepository).findAll();
        verify(this.antiqueBookRepository).findAll();
    }
}

