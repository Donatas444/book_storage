package com.example.book_storage.controller;

import static org.mockito.Mockito.when;

import com.example.book_storage.service.AllBooksService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AllBooksController.class})
@ExtendWith(SpringExtension.class)
public class AllBooksControllerTest {
    @Autowired
    private AllBooksController allBooksController;

    @MockBean
    private AllBooksService allBooksService;

    @Test
    public void testGetAllStock() throws Exception {
        when(this.allBooksService.allBooksByQuantity()).thenReturn(new ArrayList<Object>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all");
        MockMvcBuilders.standaloneSetup(this.allBooksController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllStock2() throws Exception {
        when(this.allBooksService.allBooksByQuantity()).thenReturn(new ArrayList<Object>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all");
        MockMvcBuilders.standaloneSetup(this.allBooksController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

