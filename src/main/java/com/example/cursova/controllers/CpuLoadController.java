package com.example.cursova.controllers;

import com.example.cursova.config.LanguageManager;
import com.example.cursova.services.hardware.CpuMonitor;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import java.util.ResourceBundle;

public class CpuLoadController {
    @FXML
    private PieChart loadChart;

    @FXML
    private void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        updateChart();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateChart();
            }
        };
        timer.start();
    }

    private void updateChart() {
        double[] load = CpuMonitor.getCpuLoad();
        double avgLoad = 0;
        for (double v : load) {
            avgLoad += v;
        }
        avgLoad = (avgLoad / load.length) * 100;
        double idleLoad = 100 - avgLoad;

        PieChart.Data usedData = new PieChart.Data("Used (" + String.format("%.1f%%)", avgLoad), avgLoad);
        PieChart.Data idleData = new PieChart.Data("Idle (" + String.format("%.1f%%)", idleLoad), idleLoad);

        loadChart.getData().setAll(usedData, idleData);
    }
}