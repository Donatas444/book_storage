package com.example.book_storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.model.RegularBook;
import com.example.book_storage.repository.AntiqueBookRepository;
import com.example.book_storage.repository.RegularBookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AntiqueBookService.class, AllBooksService.class})
@ExtendWith(SpringExtension.class)
class AntiqueBookServiceTest {
    @MockBean
    private AllBooksService allBooksService;

    @MockBean
    private AntiqueBookRepository antiqueBookRepository;

    @Autowired
    private AntiqueBookService antiqueBookService;

    @MockBean
    private RegularBookRepository regularBookRepository;

    @Test
    void testAddAntiqueOrRegularBook() {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookRepository.save(any())).thenReturn(regularBook);

        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        this.antiqueBookService.addAntiqueOrRegularBook(antiqueBook);
        verify(this.regularBookRepository).save(any());
    }

    @Test
    void testAddAntiqueOrRegularBook2() throws IllegalArgumentException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookRepository.save(any())).thenReturn(regularBook);

        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        when(this.antiqueBookRepository.save(any())).thenReturn(antiqueBook);
        doNothing().when(this.allBooksService).validateBarcode(anyString());
        doNothing().when(this.allBooksService).priceValidation(anyDouble());

        AntiqueBook antiqueBook1 = new AntiqueBook();
        antiqueBook1.setPublishingYear(LocalDate.ofYearDay(1, 1));
        this.antiqueBookService.addAntiqueOrRegularBook(antiqueBook1);
        verify(this.antiqueBookRepository).save(any());
        verify(this.allBooksService).priceValidation(anyDouble());
        verify(this.allBooksService).validateBarcode(anyString());
    }

    @Test
    void testFindByBarcode() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        when(this.antiqueBookRepository.findByBarcode(anyString())).thenReturn(antiqueBook);
        assertSame(antiqueBook, this.antiqueBookService.findByBarcode("Barcode"));
        verify(this.antiqueBookRepository).findByBarcode(anyString());
    }

    @Test
    void testEditBookByBarcode() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");

        AntiqueBook antiqueBook1 = new AntiqueBook();
        antiqueBook1.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook1.setPrice(10.0);
        antiqueBook1.setBookName("Book Name");
        antiqueBook1.setId(123L);
        antiqueBook1.setBarcode("Barcode");
        antiqueBook1.setQuantity(1);
        antiqueBook1.setAuthor("JaneDoe");
        when(this.antiqueBookRepository.save(any())).thenReturn(antiqueBook1);
        when(this.antiqueBookRepository.findByBarcode(anyString())).thenReturn(antiqueBook);
        this.antiqueBookService.editBookByBarcode("Barcode", 10.0);
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    void testCalculateTotalPrice() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        when(this.antiqueBookRepository.findByBarcode(anyString())).thenReturn(antiqueBook);
        assertEquals(51.0, this.antiqueBookService.calculateTotalPrice("Barcode"));
        verify(this.antiqueBookRepository).findByBarcode(anyString());
    }

    @Test
    void testYearToInt() {
        assertEquals(51, this.antiqueBookService.yearToInt(LocalDate.ofEpochDay(1L)));
        assertEquals(2022, this.antiqueBookService.yearToInt(LocalDate.ofYearDay(-1, 1)));
    }

    @Test
    void testAllBarcodesByQuantity() throws NullPointerException {
        when(this.antiqueBookRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.antiqueBookService.allBarcodesByQuantity().isEmpty());
        verify(this.antiqueBookRepository).findAll();
    }

    @Test
    void testAllBarcodesByQuantity2() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(0);
        antiqueBook.setAuthor("JaneDoe");

        ArrayList<AntiqueBook> antiqueBookList = new ArrayList<>();
        antiqueBookList.add(antiqueBook);
        when(this.antiqueBookRepository.findAll()).thenReturn(antiqueBookList);
        List<String> actualAllBarcodesByQuantityResult = this.antiqueBookService.allBarcodesByQuantity();
        assertEquals(1, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        verify(this.antiqueBookRepository).findAll();
    }

    @Test
    void testAllBarcodesByQuantity3() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(0);
        antiqueBook.setAuthor("JaneDoe");

        AntiqueBook antiqueBook1 = new AntiqueBook();
        antiqueBook1.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook1.setPrice(10.0);
        antiqueBook1.setBookName("Book Name");
        antiqueBook1.setId(123L);
        antiqueBook1.setBarcode("Barcode");
        antiqueBook1.setQuantity(0);
        antiqueBook1.setAuthor("JaneDoe");

        ArrayList<AntiqueBook> antiqueBookList = new ArrayList<>();
        antiqueBookList.add(antiqueBook1);
        antiqueBookList.add(antiqueBook);
        when(this.antiqueBookRepository.findAll()).thenReturn(antiqueBookList);
        List<String> actualAllBarcodesByQuantityResult = this.antiqueBookService.allBarcodesByQuantity();
        assertEquals(2, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(1));
        verify(this.antiqueBookRepository).findAll();
    }
}

