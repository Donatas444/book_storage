package com.example.book_storage.controller;

import com.example.book_storage.service.AllBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AllBooksController {
    @Autowired
    AllBooksService allBooksService;

    @GetMapping("/all")
    public List<Object> getAllStock() {
        return allBooksService.allBooksByQuantity();
    }
}
