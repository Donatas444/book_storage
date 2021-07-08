package com.example.book_storage.controller;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.example.book_storage.service.BarcodeService;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BarcodeController.class})
@ExtendWith(SpringExtension.class)
public class BarcodeControllerTest {
    @Autowired
    private BarcodeController barcodeController;

    @MockBean
    private BarcodeService barcodeService;

    @Test
    public void testPrintBarcode() throws Exception {
        when(this.barcodeService.generateBarcodeImage(anyString())).thenReturn(new BufferedImage(1, 1, 1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/print/{barcode}", "Barcode");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.barcodeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }
}

