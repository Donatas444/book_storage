package com.example.book_storage.swagger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfigurationTest {
    @Test
    public void testApi() {
        Docket actualApiResult = (new SwaggerConfiguration()).api();
        assertTrue(actualApiResult.isEnabled());
        assertEquals("default", actualApiResult.getGroupName());
    }
}

