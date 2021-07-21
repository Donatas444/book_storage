package com.example.book_storage.service;

import com.example.book_storage.model.ScienceJournal;
import com.example.book_storage.repository.ScienceJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScienceJournalService {
    @Autowired
    ScienceJournalRepository scienceJournalRepository;
    @Autowired
    AllBooksService allBooksService;

    public void addJournal(ScienceJournal journal) throws IllegalArgumentException {
        if (journal.getScienceIndex() <= 10) {
            ScienceJournal sJournal = new ScienceJournal();
            sJournal.setBookName(journal.getBookName());
            sJournal.setAuthor(journal.getAuthor());
            allBooksService.priceValidation(journal.getPrice());
            sJournal.setPrice(journal.getPrice());
            sJournal.setQuantity(journal.getQuantity());
            sJournal.setScienceIndex(journal.getScienceIndex());
            allBooksService.validateBarcode(journal.getBarcode());
            sJournal.setBarcode(journal.getBarcode());
            scienceJournalRepository.save(sJournal);
        } else throw new IllegalArgumentException("Science index can not be bigger than 10");
    }

    public ScienceJournal findByBarcode(String barcode) {
        return scienceJournalRepository.findByBarcode(barcode);
    }

    public void editJournalByBarcode(String barcode, String tableColumn, String text) throws NullPointerException {
        ScienceJournal journal = findByBarcode(barcode);
        switch (tableColumn) {
            case "name":
                journal.setBookName(text);
                scienceJournalRepository.save(journal);
                break;
            case "author":
                journal.setAuthor(text);
                scienceJournalRepository.save(journal);
                break;
            case "barcode":
                journal.setBarcode(text);
                scienceJournalRepository.save(journal);
                break;
            default:
                throw new IllegalArgumentException("Wrong field name.");
        }
    }

    public void editJournalByBarcode(String barcode, int quantity) throws NullPointerException {
        ScienceJournal journal = findByBarcode(barcode);
        journal.setQuantity(quantity);
        scienceJournalRepository.save(journal);
    }

    public void editJournalByBarcode(String barcode, double price) throws NullPointerException {
        ScienceJournal journal = findByBarcode(barcode);
        journal.setPrice(price);
        scienceJournalRepository.save(journal);
    }

    public double calculateTotalPrice(String barcode) throws NullPointerException {
        ScienceJournal journal = findByBarcode(barcode);
        return journal.getQuantity() * journal.getPrice() * journal.getScienceIndex();
    }

    public List<String> allBarcodesByQuantity() throws NullPointerException {
        List<ScienceJournal> journals = scienceJournalRepository.findAll();
        List<String> barcodes = new ArrayList<>();
        List<ScienceJournal> sortedBooks = journals
                .stream()
                .sorted(Comparator.comparing(ScienceJournal::getQuantity))
                .collect(Collectors.toList());
        sortedBooks
                .forEach(book -> barcodes.add(book.getBarcode()));
        return barcodes;
    }
}
