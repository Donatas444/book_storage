package com.example.book_storage.service;

import com.example.book_storage.model.RegularBook;
import com.example.book_storage.repository.RegularBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegularBookService {
    @Autowired
    RegularBookRepository regularBookRepository;

    public void addBook(RegularBook book) throws Exception {
        if (book.getBarcode().length() == 12 || book.getBarcode().length() == 8) {
            RegularBook newBook = new RegularBook();
            newBook.setBookName(book.getBookName());
            newBook.setAuthor(book.getAuthor());
            newBook.setPrice(book.getPrice());
            newBook.setQuantity(book.getQuantity());
            newBook.setBarcode(book.getBarcode());
            regularBookRepository.save(newBook);
        } else throw new Exception("Barcode length must be 8, or 12 chars/symbols");
    }

    public RegularBook findByBarcode(String barcode) throws NullPointerException {
        return regularBookRepository.findByBarcode(barcode);
    }

    public void editBookByBarcode(String barcode, String tableColumn, String text) throws NullPointerException {
        RegularBook book = findByBarcode(barcode);
        switch (tableColumn) {
            case "name":
                book.setBookName(text);
                regularBookRepository.save(book);
                break;
            case "author":
                book.setAuthor(text);
                regularBookRepository.save(book);
                break;
            case "barcode":
                book.setBarcode(text);
                regularBookRepository.save(book);
                break;
        }
    }

    public void editBookByBarcode(String barcode, int quantity) throws NullPointerException {
        RegularBook book = findByBarcode(barcode);
        book.setQuantity(quantity);
        regularBookRepository.save(book);
    }

    public void editBookByBarcode(String barcode, double price) throws NullPointerException {
        RegularBook book = findByBarcode(barcode);
        book.setPrice(price);
        regularBookRepository.save(book);
    }

    public double calculateTotalPrice(String barcode) throws NullPointerException {
        RegularBook book = findByBarcode(barcode);
        return book.getQuantity() * book.getPrice();
    }

    public List<String> allBarcodesByQuantity() throws NullPointerException {
        List<RegularBook> books = regularBookRepository.findAll();
        List<String> barcodes = new ArrayList<>();
        List<RegularBook> sortedBooks = books
                .stream()
                .sorted(Comparator.comparing(RegularBook::getQuantity))
                .collect(Collectors.toList());
        sortedBooks
                .forEach(book -> barcodes.add(book.getBarcode()));
        return barcodes;
    }
}
