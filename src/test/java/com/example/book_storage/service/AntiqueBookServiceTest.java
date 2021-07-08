package com.example.book_storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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

@ContextConfiguration(classes = {AntiqueBookService.class})
@ExtendWith(SpringExtension.class)
public class AntiqueBookServiceTest {
    @MockBean
    private AntiqueBookRepository antiqueBookRepository;

    @Autowired
    private AntiqueBookService antiqueBookService;

    @MockBean
    private RegularBookRepository regularBookRepository;

    @Test
    public void testAddAntiqueOrRegularBook() {
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
    public void testAddAntiqueOrRegularBook2() {
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

        AntiqueBook antiqueBook1 = new AntiqueBook();
        antiqueBook1.setPublishingYear(LocalDate.ofYearDay(1, 1));
        this.antiqueBookService.addAntiqueOrRegularBook(antiqueBook1);
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    public void testValidateBarcodeAndAddBook() throws Exception {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setBarcode("Barcode");
        assertThrows(Exception.class, () -> this.antiqueBookService.validateBarcodeAndAddBook(antiqueBook));
    }

    @Test
    public void testValidateBarcodeAndAddBook2() throws Exception {
        AntiqueBook antiqueBook = mock(AntiqueBook.class);
        when(antiqueBook.getBarcode()).thenReturn("foo");
        assertThrows(Exception.class, () -> this.antiqueBookService.validateBarcodeAndAddBook(antiqueBook));
        verify(antiqueBook, times(2)).getBarcode();
    }

    @Test
    public void testFindByBarcode() throws NullPointerException {
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
    public void testEditBookByBarcode() throws NullPointerException {
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
    public void testEditBookByBarcode2() throws NullPointerException {
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
        this.antiqueBookService.editBookByBarcode("Barcode", 1);
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    public void testEditBookByBarcode3() throws NullPointerException {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        when(this.antiqueBookRepository.findByBarcode(anyString())).thenReturn(antiqueBook);
        this.antiqueBookService.editBookByBarcode("Barcode", "Table Column", "Text");
        verify(this.antiqueBookRepository).findByBarcode(anyString());
    }

    @Test
    public void testEditBookByBarcode4() throws NullPointerException {
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
        this.antiqueBookService.editBookByBarcode("Barcode", "author", "Text");
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    public void testEditBookByBarcode5() throws NullPointerException {
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
        this.antiqueBookService.editBookByBarcode("Barcode", "barcode", "Text");
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    public void testEditBookByBarcode6() throws NullPointerException {
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
        this.antiqueBookService.editBookByBarcode("Barcode", "name", "Text");
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save((AntiqueBook) any());
    }

    @Test
    public void testEditBookByBarcode7() throws NullPointerException {
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
        this.antiqueBookService.editBookByBarcode("Barcode", LocalDate.ofEpochDay(1L));
        verify(this.antiqueBookRepository).findByBarcode(anyString());
        verify(this.antiqueBookRepository).save(any());
    }

    @Test
    public void testCalculateTotalPrice() throws NullPointerException {
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
    public void testYearToInt() {
        assertEquals(51, this.antiqueBookService.yearToInt(LocalDate.ofEpochDay(1L)));
        assertEquals(2022, this.antiqueBookService.yearToInt(LocalDate.ofYearDay(-1, 1)));
    }

    @Test
    public void testAllBarcodesByQuantity() throws NullPointerException {
        when(this.antiqueBookRepository.findAll()).thenReturn(new ArrayList<AntiqueBook>());
        assertTrue(this.antiqueBookService.allBarcodesByQuantity().isEmpty());
        verify(this.antiqueBookRepository).findAll();
    }

    @Test
    public void testAllBarcodesByQuantity2() throws NullPointerException {
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
    public void testAllBarcodesByQuantity3() throws NullPointerException {
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

