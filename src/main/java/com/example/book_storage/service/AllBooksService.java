package com.example.book_storage.service;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.model.RegularBook;
import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.repository.AntiqueBookRepository;
import com.example.book_storage.repository.RegularBookRepository;
import com.example.book_storage.repository.ScienceJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AllBooksService {
    @Autowired
    AntiqueBookRepository antiqueBookRepository;
    @Autowired
    RegularBookRepository regularBookRepository;
    @Autowired
    ScienceJournalRepository scienceJournalRepository;
    @Autowired
    AntiqueBookService antiqueBookService;
    @Autowired
    RegularBookService regularBookService;
    @Autowired
    ScienceJournalService scienceJournalService;

    public List<Object> allBooksByQuantity() {
        List<Object> allBooks = new ArrayList<>();
        List<RegularBook> regularBookList = regularBookRepository.findAll();
        List<ScienceJournal> scienceJournalList = scienceJournalRepository.findAll();
        List<AntiqueBook> antiqueBookList = antiqueBookRepository.findAll();
        List<RegularBook> sortedRegularBookList = regularBookList.stream()
                .sorted(Comparator.comparing(RegularBook::getQuantity))
                .collect(Collectors.toList());
        List<AntiqueBook> sortedAntiqueList = antiqueBookList.stream()
                .sorted(Comparator.comparing(AntiqueBook::getQuantity))
                .collect(Collectors.toList());
        List<ScienceJournal> sortedJournalList = scienceJournalList.stream()
                .sorted(Comparator.comparing(ScienceJournal::getQuantity))
                .collect(Collectors.toList());
        allBooks.addAll(sortedRegularBookList);
        allBooks.addAll(sortedAntiqueList);
        allBooks.addAll(sortedJournalList);
        return allBooks;
    }

    private boolean isBarcodeAlready(String barcode) {
        List<String> allBarcodes = new ArrayList<>();
        allBarcodes.addAll(antiqueBookService.allBarcodesByQuantity());
        allBarcodes.addAll(regularBookService.allBarcodesByQuantity());
        allBarcodes.addAll(scienceJournalService.allBarcodesByQuantity());
        boolean value = false;
        if (allBarcodes.stream().anyMatch(barcode::equals)) {
            value = true;
        }
        return value;
    }

    private boolean validateBarcodeDigits(String barcode) throws IllegalArgumentException {
        boolean value = false;
        if (barcode.length() == 12 || barcode.length() == 8) {
            value = true;
        }
        return value;
    }

    public void validateBarcode(String barcode) throws IllegalArgumentException {
        if (!validateBarcodeDigits(barcode)) {
            throw new IllegalArgumentException("Barcode length must be 8, or 12 chars/symbols");
        } else if (isBarcodeAlready(barcode)) {
            throw new IllegalArgumentException("Barcode already exists");
        }
    }

    public void priceValidation(double price) {
        String money = String.valueOf(price);
        Pattern pattern = Pattern.compile("[0-9]+([,.][0-9]{1,2})?");
        Matcher matcher = pattern.matcher(money);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Bad price format!");
        }
    }
}