package com.example.book_storage.service;

import com.example.book_storage.model.AntiqueBook;
import com.example.book_storage.model.RegularBook;
import com.example.book_storage.repository.AntiqueBookRepository;
import com.example.book_storage.repository.RegularBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AntiqueBookService {
    @Autowired
    AntiqueBookRepository antiqueBookRepository;
    @Autowired
    RegularBookRepository regularBookRepository;
    @Autowired
    AllBooksService allBooksService;

    public void addAntiqueOrRegularBook(AntiqueBook book) {
        LocalDate limit = LocalDate.of(1900, 1, 1);
        if (limit.compareTo(book.getPublishingYear()) >= 0) {
            AntiqueBook newBook = new AntiqueBook();
            newBook.setBookName(book.getBookName());
            newBook.setAuthor(book.getAuthor());
            allBooksService.priceValidation(book.getPrice());
            newBook.setPrice(book.getPrice());
            newBook.setQuantity(book.getQuantity());
            allBooksService.validateBarcode(book.getBarcode());
            newBook.setBarcode(book.getBarcode());
            newBook.setPublishingYear(book.getPublishingYear());
            antiqueBookRepository.save(newBook);
        } else {
            RegularBook regularBook = new RegularBook();
            regularBook.setBookName(book.getBookName());
            regularBook.setAuthor(book.getAuthor());
            regularBook.setPrice(book.getPrice());
            regularBook.setQuantity(book.getQuantity());
            regularBook.setBarcode(book.getBarcode());
            regularBookRepository.save(regularBook);
        }
    }

    public AntiqueBook findByBarcode(String barcode) throws NullPointerException {
        return antiqueBookRepository.findByBarcode(barcode);
    }

    public void editBookByBarcode(String barcode, String tableColumn, String text) throws NullPointerException {
        AntiqueBook book = findByBarcode(barcode);
        switch (tableColumn) {
            case "name":
                book.setBookName(text);
                antiqueBookRepository.save(book);
                break;
            case "author":
                book.setAuthor(text);
                antiqueBookRepository.save(book);
                break;
            case "barcode":
                book.setBarcode(text);
                antiqueBookRepository.save(book);
                break;
            default:
                throw new IllegalArgumentException("Wrong field name.");
        }
    }

    public void editBookByBarcode(String barcode, int quantity) throws NullPointerException {
        AntiqueBook book = findByBarcode(barcode);
        book.setQuantity(quantity);
        antiqueBookRepository.save(book);
    }

    public void editBookByBarcode(String barcode, double price) throws NullPointerException {
        AntiqueBook book = findByBarcode(barcode);
        book.setPrice(price);
        antiqueBookRepository.save(book);
    }

    public void editBookByBarcode(String barcode, LocalDate year) throws NullPointerException {
        AntiqueBook book = findByBarcode(barcode);
        book.setPublishingYear(year);
        antiqueBookRepository.save(book);
    }

    public double calculateTotalPrice(String barcode) throws NullPointerException {
        AntiqueBook book = findByBarcode(barcode);
        return book.getQuantity() * book.getPrice() * yearToInt(book.getPublishingYear()) / 10;
    }

    public int yearToInt(LocalDate date) {
        return (Period.between(date, LocalDate.now())).getYears();
    }

    public List<String> allBarcodesByQuantity() throws NullPointerException {
        List<AntiqueBook> books = antiqueBookRepository.findAll();
        List<String> barcodes = new ArrayList<>();
        List<AntiqueBook> sortedBooks = books
                .stream()
                .sorted(Comparator.comparing(AntiqueBook::getQuantity))
                .collect(Collectors.toList());
        sortedBooks
                .forEach(book -> barcodes.add(book.getBarcode()));
        return barcodes;
    }
}

