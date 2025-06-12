package com.example.cursova.controllers;

import com.example.cursova.models.GpuInfo;
import com.example.cursova.services.hardware.GpuMonitor;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GpuController {
    @FXML
    private TableView<GpuProperty> gpuTable;
    @FXML
    private TableColumn<GpuProperty, String> propertyColumn;
    @FXML
    private TableColumn<GpuProperty, String> valueColumn;

    private ObservableList<GpuProperty> gpuData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        gpuTable.setItems(gpuData);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGpuInfo();
            }
        };
        timer.start();
    }

    private void updateGpuInfo() {
        GpuInfo info = GpuMonitor.getGpuInfo();
        gpuData.clear();
        gpuData.add(new GpuProperty("Model", info.getModel()));
        double memoryGB = info.getMemory() / 1_073_741_824.0;
        gpuData.add(new GpuProperty("Memory", memoryGB > 0 ? String.format("%.1f GB", memoryGB) : "N/A"));
        gpuData.add(new GpuProperty("Driver", info.getDriver()));
        gpuData.add(new GpuProperty("Core Clock", String.format("%d MHz", info.getCoreClock())));
        gpuData.add(new GpuProperty("Memory Clock", String.format("%d MHz", info.getMemoryClock())));
    }

    public static class GpuProperty {
        private final String property;
        private final String value;

        public GpuProperty(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() { return property; }
        public String getValue() { return value; }
    }
}