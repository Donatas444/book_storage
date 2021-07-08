package com.example.book_storage.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.service.AntiqueBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AntiqueBookController.class})
@ExtendWith(SpringExtension.class)
public class AntiqueBookControllerTest {
    @Autowired
    private AntiqueBookController antiqueBookController;

    @MockBean
    private AntiqueBookService antiqueBookService;

    @Test
    public void testAddBook() throws Exception {
        doNothing().when(this.antiqueBookService).validateBarcodeAndAddBook(any());

        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(null);
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        String content = (new ObjectMapper()).writeValueAsString(antiqueBook);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/abook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditBookByBarcode3() throws Exception {
        doNothing().when(this.antiqueBookService).editBookByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/abook/{barcode}/{column}/{value}",
                "Column", "42", "Barcode");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditBookByBarcode4() throws Exception {
        doNothing().when(this.antiqueBookService).editBookByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/abook/{barcode}/{column}/{value}", "Column",
                "42", "Barcode");
        putResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCalculateTotalPriceByBarcode() throws Exception {
        when(this.antiqueBookService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abooks/price/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testCalculateTotalPriceByBarcode2() throws Exception {
        when(this.antiqueBookService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/abooks/price/{barcode}", "Barcode");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testEditBookByBarcode() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/abook/{barcode}/price/{value}", 10.0,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditBookByBarcode2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/abook/{barcode}/quantity/{value}", 42,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditBookByBarcode5() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/abook/{barcode}/year/*", "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    @Test
    public void testFindBookByBarcode() throws Exception {
        AntiqueBook antiqueBook = new AntiqueBook();
        antiqueBook.setPublishingYear(LocalDate.ofEpochDay(1L));
        antiqueBook.setPrice(10.0);
        antiqueBook.setBookName("Book Name");
        antiqueBook.setId(123L);
        antiqueBook.setBarcode("Barcode");
        antiqueBook.setQuantity(1);
        antiqueBook.setAuthor("JaneDoe");
        when(this.antiqueBookService.findByBarcode(anyString())).thenReturn(antiqueBook);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abook/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"author\":\"JaneDoe\",\"bookName\":\"Book Name\",\"quantity\":1,\"price\":10.0,\"barcode\":\"Barcode\","
                                        + "\"publishingYear\":[1970,1,2]}"));
    }

    @Test
    public void testGetAllBarcodesByQuantity() throws Exception {
        when(this.antiqueBookService.allBarcodesByQuantity()).thenReturn(new ArrayList<String>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abarcodes");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllBarcodesByQuantity2() throws Exception {
        when(this.antiqueBookService.allBarcodesByQuantity()).thenReturn(new ArrayList<String>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/abarcodes");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.antiqueBookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

