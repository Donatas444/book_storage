package com.example.book_storage.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.RegularBook;
import com.example.book_storage.service.RegularBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

@ContextConfiguration(classes = {RegularBookController.class})
@ExtendWith(SpringExtension.class)
public class RegularBookControllerTest {
    @Autowired
    private RegularBookController regularBookController;

    @MockBean
    private RegularBookService regularBookService;

    @Test
    public void testAddBook() throws Exception {
        doNothing().when(this.regularBookService).addBook(any());

        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        String content = (new ObjectMapper()).writeValueAsString(regularBook);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditBookByBarcode3() throws Exception {
        doNothing().when(this.regularBookService).editBookByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/{barcode}/{column}/{value}",
                "Column", "42", "Barcode");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditBookByBarcode4() throws Exception {
        doNothing().when(this.regularBookService).editBookByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/book/{barcode}/{column}/{value}", "Column",
                "42", "Barcode");
        putResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCalculateTotalPriceByBarcode() throws Exception {
        when(this.regularBookService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/price/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testCalculateTotalPriceByBarcode2() throws Exception {
        when(this.regularBookService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/books/price/{barcode}", "Barcode");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testEditBookByBarcode() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/{barcode}/price/{value}", 10.0,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditBookByBarcode2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/{barcode}/quantity/{value}", 42,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testFindBookByBarcode() throws Exception {
        RegularBook regularBook = new RegularBook();
        regularBook.setPrice(10.0);
        regularBook.setBookName("Book Name");
        regularBook.setId(123L);
        regularBook.setBarcode("Barcode");
        regularBook.setQuantity(1);
        regularBook.setAuthor("JaneDoe");
        when(this.regularBookService.findByBarcode(anyString())).thenReturn(regularBook);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/book/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"author\":\"JaneDoe\",\"bookName\":\"Book Name\",\"quantity\":1,\"price\":10.0,\"barcode\":\"Barcode\"}"));
    }

    @Test
    public void testGetAllBarcodesByQuantity() throws Exception {
        when(this.regularBookService.allBarcodesByQuantity()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/barcodes");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllBarcodesByQuantity2() throws Exception {
        when(this.regularBookService.allBarcodesByQuantity()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/barcodes");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.regularBookController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

