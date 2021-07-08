package com.example.book_storage.service;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class BarcodeService {
    public BufferedImage generateBarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        return BarcodeImageHandler.getImage(barcode);
    }
}
