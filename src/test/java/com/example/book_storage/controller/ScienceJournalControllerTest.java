package com.example.book_storage.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.service.ScienceJournalService;
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

@ContextConfiguration(classes = {ScienceJournalController.class})
@ExtendWith(SpringExtension.class)
public class ScienceJournalControllerTest {
    @Autowired
    private ScienceJournalController scienceJournalController;

    @MockBean
    private ScienceJournalService scienceJournalService;

    @Test
    public void testAddBook() throws Exception {
        doNothing().when(this.scienceJournalService).validateBarcodeAndAddJournal(any());

        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        String content = (new ObjectMapper()).writeValueAsString(scienceJournal);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/journal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCalculateTotalPriceByBarcode() throws Exception {
        when(this.scienceJournalService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/journals/price/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testCalculateTotalPriceByBarcode2() throws Exception {
        when(this.scienceJournalService.calculateTotalPrice(anyString())).thenReturn(10.0);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/journals/price/{barcode}", "Barcode");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    public void testEditBookByBarcode() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/journal/{barcode}/price/{value}", 10.0,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditBookByBarcode2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/journal/{barcode}/quantity/{value}", 42,
                "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testEditBookByBarcode3() throws Exception {
        doNothing().when(this.scienceJournalService).editJournalByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/journal/{barcode}/{column}/{value}",
                "Column", "42", "Barcode");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditBookByBarcode4() throws Exception {
        doNothing().when(this.scienceJournalService).editJournalByBarcode(anyString(), anyString(), anyString());
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/journal/{barcode}/{column}/{value}",
                "Column", "42", "Barcode");
        putResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFindBookByBarcode() throws Exception {
        ScienceJournal scienceJournal = new ScienceJournal();
        scienceJournal.setScienceIndex(1);
        scienceJournal.setPrice(10.0);
        scienceJournal.setBookName("Book Name");
        scienceJournal.setId(123L);
        scienceJournal.setBarcode("Barcode");
        scienceJournal.setQuantity(1);
        scienceJournal.setAuthor("JaneDoe");
        when(this.scienceJournalService.findByBarcode(anyString())).thenReturn(scienceJournal);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/journal/{barcode}", "Barcode");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"author\":\"JaneDoe\",\"bookName\":\"Book Name\",\"quantity\":1,\"price\":10.0,\"barcode\":\"Barcode\","
                                        + "\"scienceIndex\":1}"));
    }

    @Test
    public void testGetAllBarcodesByQuantity() throws Exception {
        when(this.scienceJournalService.allBarcodesByQuantity()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sjbarcodes");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllBarcodesByQuantity2() throws Exception {
        when(this.scienceJournalService.allBarcodesByQuantity()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/sjbarcodes");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.scienceJournalController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

