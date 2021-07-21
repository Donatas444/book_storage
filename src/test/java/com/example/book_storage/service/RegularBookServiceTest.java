package com.example.book_storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.RegularBook;
import com.example.book_storage.repository.RegularBookRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RegularBookService.class, AllBooksService.class})
@ExtendWith(SpringExtension.class)
class RegularBookServiceTest {
    @MockBean
    private AllBooksService allBooksService;

    @MockBean
    private RegularBookRepository regularBookRepository;

    @Autowired
    private RegularBookService regularBookService;

    @Test
    void testAddBook() throws IllegalArgumentException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookRepository.save(any())).thenReturn(regularBook);
        doNothing().when(this.allBooksService).validateBarcode(anyString());
        doNothing().when(this.allBooksService).priceValidation(anyDouble());
        this.regularBookService.addBook(new RegularBook());
        verify(this.regularBookRepository).save(any());
        verify(this.allBooksService).priceValidation(anyDouble());
        verify(this.allBooksService).validateBarcode(anyString());
    }

    @Test
    void testFindByBarcode() throws NullPointerException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookRepository.findByBarcode(anyString())).thenReturn(regularBook);
        assertSame(regularBook, this.regularBookService.findByBarcode("Barcode"));
        verify(this.regularBookRepository).findByBarcode(anyString());
    }

    @Test
    void testEditBookByBarcode() throws NullPointerException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");

        RegularBook regularBook1 = new RegularBook();
        regularBook1.setPrice(10.0);
        regularBook1.setBookName("Book Name");
        regularBook1.setId(123L);
        regularBook1.setBarcode("Barcode");
        regularBook1.setQuantity(1);
        regularBook1.setAuthor("JaneDoe");
        when(this.regularBookRepository.save(any())).thenReturn(regularBook1);
        when(this.regularBookRepository.findByBarcode(anyString())).thenReturn(regularBook);
        this.regularBookService.editBookByBarcode("Barcode", 10.0);
        verify(this.regularBookRepository).findByBarcode(anyString());
        verify(this.regularBookRepository).save(any());
    }

    @Test
    void testCalculateTotalPrice() throws NullPointerException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookRepository.findByBarcode(anyString())).thenReturn(regularBook);
        assertEquals(10.0, this.regularBookService.calculateTotalPrice("Barcode"));
        verify(this.regularBookRepository).findByBarcode(anyString());
    }

    @Test
    void testAllBarcodesByQuantity() throws NullPointerException {
        when(this.regularBookRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.regularBookService.allBarcodesByQuantity().isEmpty());
        verify(this.regularBookRepository).findAll();
    }

    @Test
    void testAllBarcodesByQuantity2() throws NullPointerException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(0);
        regularBook.setAuthor("JaneDoe");

        ArrayList<RegularBook> regularBookList = new ArrayList<>();
        regularBookList.add(regularBook);
        when(this.regularBookRepository.findAll()).thenReturn(regularBookList);
        List<String> actualAllBarcodesByQuantityResult = this.regularBookService.allBarcodesByQuantity();
        assertEquals(1, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        verify(this.regularBookRepository).findAll();
    }

    @Test
    void testAllBarcodesByQuantity3() throws NullPointerException {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(0);
        regularBook.setAuthor("JaneDoe");

        RegularBook regularBook1 = new RegularBook();
        regularBook1.setPrice(10.0);
        regularBook1.setBookName("Book Name");
        regularBook1.setId(123L);
        regularBook1.setBarcode("Barcode");
        regularBook1.setQuantity(0);
        regularBook1.setAuthor("JaneDoe");

        ArrayList<RegularBook> regularBookList = new ArrayList<>();
        regularBookList.add(regularBook1);
        regularBookList.add(regularBook);
        when(this.regularBookRepository.findAll()).thenReturn(regularBookList);
        List<String> actualAllBarcodesByQuantityResult = this.regularBookService.allBarcodesByQuantity();
        assertEquals(2, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(1));
        verify(this.regularBookRepository).findAll();
    }
}

