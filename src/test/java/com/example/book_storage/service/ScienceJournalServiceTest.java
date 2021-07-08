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

import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.repository.ScienceJournalRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ScienceJournalService.class})
@ExtendWith(SpringExtension.class)
public class ScienceJournalServiceTest {
    @MockBean
    private ScienceJournalRepository scienceJournalRepository;

    @Autowired
    private ScienceJournalService scienceJournalService;

    @Test
    public void testAddJournal() throws Exception {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal);
        this.scienceJournalService.addJournal(new ScienceJournal());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testValidateBarcodeAndAddJournal() throws Exception {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setBarcode("Barcode");
        assertThrows(Exception.class, () -> this.scienceJournalService.validateBarcodeAndAddJournal(scienceJournal));
    }

    @Test
    public void testValidateBarcodeAndAddJournal2() throws Exception {
        ScienceJournal scienceJournal = mock(ScienceJournal.class);
        when(scienceJournal.getBarcode()).thenReturn("foo");
        assertThrows(Exception.class, () -> this.scienceJournalService.validateBarcodeAndAddJournal(scienceJournal));
        verify(scienceJournal, times(2)).getBarcode();
    }

    @Test
    public void testFindByBarcode() {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        assertSame(scienceJournal, this.scienceJournalService.findByBarcode("Barcode"));
        verify(this.scienceJournalRepository).findByBarcode(anyString());
    }

    @Test
    public void testEditJournalByBarcode() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(1);
        scienceJournal1.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal1);
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", 10.0);
        verify(this.scienceJournalRepository).findByBarcode(anyString());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testEditJournalByBarcode2() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(1);
        scienceJournal1.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal1);
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", 1);
        verify(this.scienceJournalRepository).findByBarcode(anyString());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testEditJournalByBarcode3() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", "Table Column", "Text");
        verify(this.scienceJournalRepository).findByBarcode(anyString());
    }

    @Test
    public void testEditJournalByBarcode4() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(1);
        scienceJournal1.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal1);
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", "author", "Text");
        verify(this.scienceJournalRepository).findByBarcode(anyString());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testEditJournalByBarcode5() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(1);
        scienceJournal1.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal1);
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", "barcode", "Text");
        verify(this.scienceJournalRepository).findByBarcode(anyString());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testEditJournalByBarcode6() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(1);
        scienceJournal1.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.save(any())).thenReturn(scienceJournal1);
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        this.scienceJournalService.editJournalByBarcode("Barcode", "name", "Text");
        verify(this.scienceJournalRepository).findByBarcode(anyString());
        verify(this.scienceJournalRepository).save(any());
    }

    @Test
    public void testCalculateTotalPrice() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        when(this.scienceJournalRepository.findByBarcode(anyString())).thenReturn(scienceJournal);
        assertEquals(10.0, this.scienceJournalService.calculateTotalPrice("Barcode"));
        verify(this.scienceJournalRepository).findByBarcode(anyString());
    }

    @Test
    public void testAllBarcodesByQuantity() throws NullPointerException {
        when(this.scienceJournalRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.scienceJournalService.allBarcodesByQuantity().isEmpty());
        verify(this.scienceJournalRepository).findAll();
    }

    @Test
    public void testAllBarcodesByQuantity2() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(0);
        scienceJournal.setAuthor("JaneDoe");

        ArrayList<ScienceJournal> scienceJournalList = new ArrayList<>();
        scienceJournalList.add(scienceJournal);
        when(this.scienceJournalRepository.findAll()).thenReturn(scienceJournalList);
        List<String> actualAllBarcodesByQuantityResult = this.scienceJournalService.allBarcodesByQuantity();
        assertEquals(1, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        verify(this.scienceJournalRepository).findAll();
    }

    @Test
    public void testAllBarcodesByQuantity3() throws NullPointerException {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(0);
        scienceJournal.setAuthor("JaneDoe");

        ScienceJournal scienceJournal1 = new ScienceJournal();
        scienceJournal1.setScienceIndex(1);
        scienceJournal1.setPrice(10.0);
        scienceJournal1.setBookName("Book Name");
        scienceJournal1.setId(123L);
        scienceJournal1.setBarcode("Barcode");
        scienceJournal1.setQuantity(0);
        scienceJournal1.setAuthor("JaneDoe");

        ArrayList<ScienceJournal> scienceJournalList = new ArrayList<>();
        scienceJournalList.add(scienceJournal1);
        scienceJournalList.add(scienceJournal);
        when(this.scienceJournalRepository.findAll()).thenReturn(scienceJournalList);
        List<String> actualAllBarcodesByQuantityResult = this.scienceJournalService.allBarcodesByQuantity();
        assertEquals(2, actualAllBarcodesByQuantityResult.size());
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(0));
        assertEquals("Barcode", actualAllBarcodesByQuantityResult.get(1));
        verify(this.scienceJournalRepository).findAll();
    }
}

