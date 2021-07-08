package com.example.book_storage.controller;

import com.example.book_storage.model.RegularBook;
import com.example.book_storage.service.RegularBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegularBookController {
    @Autowired
    RegularBookService regularBookService;

    @PostMapping("/book")
    public void addBook(@RequestBody RegularBook book) throws Exception {
        regularBookService.addBook(book);
    }

    @GetMapping("/book/{barcode}")
    public RegularBook findBookByBarcode(@PathVariable("barcode") String barcode) {
        return regularBookService.findByBarcode(barcode);
    }

    @PutMapping("/book/{barcode}/{column}/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("column") String columnName,
                                  @PathVariable("value") String text) throws IllegalArgumentException {
        regularBookService.editBookByBarcode(barcode, columnName, text);
    }

    @PutMapping("/book/{barcode}/price/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") double price) throws IllegalArgumentException {
        regularBookService.editBookByBarcode(barcode, price);
    }

    @PutMapping("/book/{barcode}/quantity/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") int value) throws IllegalArgumentException {
        regularBookService.editBookByBarcode(barcode, value);
    }

    @GetMapping("/books/price/{barcode}")
    public double calculateTotalPriceByBarcode(@PathVariable("barcode") String barcode) {
        return regularBookService.calculateTotalPrice(barcode);
    }

    @GetMapping("/barcodes")
    public List<String> getAllBarcodesByQuantity() {
        return regularBookService.allBarcodesByQuantity();
    }
}
