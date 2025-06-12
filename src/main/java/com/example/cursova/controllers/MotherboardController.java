package com.example.cursova.controllers;

import com.example.cursova.models.MotherboardInfo;
import com.example.cursova.services.hardware.MotherboardMonitor;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MotherboardController {
    @FXML
    private TableView<MotherboardProperty> motherboardTable;
    @FXML
    private TableColumn<MotherboardProperty, String> propertyColumn;
    @FXML
    private TableColumn<MotherboardProperty, String> valueColumn;

    private ObservableList<MotherboardProperty> motherboardData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        motherboardTable.setItems(motherboardData);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateMotherboardInfo();
            }
        };
        timer.start();
    }

    private void updateMotherboardInfo() {
        MotherboardInfo info = MotherboardMonitor.getMotherboardInfo();
        motherboardData.clear();
        motherboardData.add(new MotherboardProperty("Manufacturer", info.getManufacturer()));
        motherboardData.add(new MotherboardProperty("Model", info.getModel()));
        motherboardData.add(new MotherboardProperty("Chipset", info.getChipset()));
        motherboardData.add(new MotherboardProperty("BIOS", info.getBios()));
    }

    public static class MotherboardProperty {
        private final String property;
        private final String value;

        public MotherboardProperty(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() { return property; }
        public String getValue() { return value; }
    }
}