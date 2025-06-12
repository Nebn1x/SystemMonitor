package com.example.cursova.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final String LOG_FILE = "log.txt";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + ": " + message + "\n");
        } catch (IOException e) {
            System.err.println("Помилка запису в лог: " + e.getMessage());
        }
    }

    public static void logAppStart() {
        log("Програма запущена");
    }

    public static void logReportExport(String format, String filePath) {
        log("Експорт звіту у форматі " + format + " до " + filePath);
    }

    public static void logError(String message) {
        log("Помилка: " + message);
        System.err.println("Помилка: " + message); // Додатковий вивід у консоль
    }
}