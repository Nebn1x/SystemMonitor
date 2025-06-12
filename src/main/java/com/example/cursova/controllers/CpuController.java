package com.example.cursova.controllers;

import com.example.cursova.models.CpuInfo;
import com.example.cursova.services.hardware.CpuMonitor;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CpuController {
    @FXML
    private TableView<CpuProperty> cpuTable;
    @FXML
    private TableColumn<CpuProperty, String> propertyColumn;
    @FXML
    private TableColumn<CpuProperty, String> valueColumn;

    private ObservableList<CpuProperty> cpuData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        cpuTable.setItems(cpuData);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateCpuInfo();
            }
        };
        timer.start();
    }

    private void updateCpuInfo() {
        CpuInfo info = CpuMonitor.getCpuInfo();
        cpuData.clear();
        cpuData.add(new CpuProperty("Name", info.getName()));
        cpuData.add(new CpuProperty("Architecture", info.getArchitecture()));
        cpuData.add(new CpuProperty("Physical Cores", String.valueOf(info.getPhysicalCores())));
        cpuData.add(new CpuProperty("Logical Cores", String.valueOf(info.getLogicalCores())));
        cpuData.add(new CpuProperty("Current Frequency", String.format("%.1f GHz", info.getCurrentFrequency())));
        cpuData.add(new CpuProperty("Cache L1", info.getCacheL1()));
        cpuData.add(new CpuProperty("Cache L2", info.getCacheL2()));
        cpuData.add(new CpuProperty("Cache L3", info.getCacheL3()));
        cpuData.add(new CpuProperty("Socket", info.getSocket()));
        cpuData.add(new CpuProperty("Process", info.getProcess()));
        cpuData.add(new CpuProperty("Voltage", String.format("%.3f V", info.getVoltage())));
        cpuData.add(new CpuProperty("Family", info.getFamily()));
        cpuData.add(new CpuProperty("Model", info.getModel()));
        cpuData.add(new CpuProperty("Stepping", info.getStepping()));
        cpuData.add(new CpuProperty("Instructions", info.getInstructions()));
    }

    public static class CpuProperty {
        private final String property;
        private final String value;

        public CpuProperty(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() { return property; }
        public String getValue() { return value; }
    }
}