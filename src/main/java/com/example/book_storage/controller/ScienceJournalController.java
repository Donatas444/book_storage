package com.example.book_storage.controller;

import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.service.ScienceJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScienceJournalController {
    @Autowired
    ScienceJournalService scienceJournalService;

    @PostMapping("/journal")
    public void addBook(@RequestBody ScienceJournal journal) {
        scienceJournalService.addJournal(journal);
    }

    @GetMapping("/journal/{barcode}")
    public ScienceJournal findBookByBarcode(@PathVariable("barcode") String barcode) {
        return scienceJournalService.findByBarcode(barcode);
    }

    @PutMapping("/journal/{barcode}/{column}/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("column") String columnName,
                                  @PathVariable("value") String text) throws IllegalArgumentException {
        scienceJournalService.editJournalByBarcode(barcode, columnName, text);
    }

    @PutMapping("/journal/{barcode}/price/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") double price) throws IllegalArgumentException {
        scienceJournalService.editJournalByBarcode(barcode, price);
    }

    @PutMapping("/journal/{barcode}/quantity/{value}")
    public void editBookByBarcode(@PathVariable("barcode") String barcode,
                                  @PathVariable("value") int value) throws IllegalArgumentException {
        scienceJournalService.editJournalByBarcode(barcode, value);
    }

    @GetMapping("/journals/price/{barcode}")
    public double calculateTotalPriceByBarcode(@PathVariable("barcode") String barcode) {
        return scienceJournalService.calculateTotalPrice(barcode);
    }

    @GetMapping("/sjbarcodes")
    public List<String> getAllBarcodesByQuantity() {
        return scienceJournalService.allBarcodesByQuantity();
    }
}
