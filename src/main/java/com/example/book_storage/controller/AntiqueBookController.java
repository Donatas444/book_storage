package com.example.book_storage.controller;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.service.AntiqueBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AntiqueBookController {
    @Autowired
    AntiqueBookService antiqueBookService;

    @PostMapping("/abook")
    public void addBook(@RequestBody AntiqueBook book) {
        antiqueBookService.validateBarcodeAndAddBook(book);
    }

    @GetMapping("/abook/{barcode}")
    public AntiqueBook findBookByBarcode(@PathVariable("barcode") String barcode) {
        return antiqueBookService.findByBarcode(barcode);
    }

    @PutMapping("/abook/{barcode}/{column}/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("column") String columnName,
                                  @PathVariable("value") String text) throws IllegalArgumentException {
        antiqueBookService.editBookByBarcode(barcode, columnName, text);
    }

    @PutMapping("/abook/{barcode}/price/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") double price) throws IllegalArgumentException {
        antiqueBookService.editBookByBarcode(barcode, price);
    }

    @PutMapping("/abook/{barcode}/quantity/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") int value) throws IllegalArgumentException {
        antiqueBookService.editBookByBarcode(barcode, value);
    }

    @PutMapping("/abook/{barcode}/year/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("year") LocalDate year) throws IllegalArgumentException {
        antiqueBookService.editBookByBarcode(barcode, year);
    }

    @GetMapping("/abooks/price/{barcode}")
    public double calculateTotalPriceByBarcode(@PathVariable("barcode") String barcode) {
        return antiqueBookService.calculateTotalPrice(barcode);
    }

    @GetMapping("/abarcodes")
    public List<String> getAllBarcodesByQuantity() {
        return antiqueBookService.allBarcodesByQuantity();
    }
}

