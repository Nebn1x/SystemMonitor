package com.example.cursova.utils;

import com.example.cursova.services.ReportService;

public class TextExporter {
    public static void export(String filePath) {
        ReportService.exportToTxt(filePath);
    }
}