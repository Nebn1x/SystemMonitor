package com.example.cursova.controllers;

import com.example.cursova.config.LanguageManager;
import com.example.cursova.services.hardware.MemoryMonitor;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import java.util.ResourceBundle;

public class MemoryUsageController {
    @FXML
    private PieChart usageChart;

    @FXML
    private void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        updateChart();
    }

    private void updateChart() {
        double totalMemory = MemoryMonitor.getMemoryInfo().getTotalMemory() / 1_073_741_824.0;
        double usedMemory = MemoryMonitor.getMemoryInfo().getUsedMemory() / 1_073_741_824.0;
        double freeMemory = totalMemory - usedMemory;

        PieChart.Data usedData = new PieChart.Data("Used (" + String.format("%.1f%%)", (usedMemory / totalMemory) * 100), usedMemory);
        PieChart.Data freeData = new PieChart.Data("Free (" + String.format("%.1f%%)", (freeMemory / totalMemory) * 100), freeMemory);

        usageChart.getData().setAll(usedData, freeData);
    }
}