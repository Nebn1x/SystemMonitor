package com.example.cursova.controllers;

import com.example.cursova.config.LanguageManager;
import com.example.cursova.models.MemoryInfo;
import com.example.cursova.services.hardware.MemoryMonitor;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ResourceBundle;

public class MemoryController {
    @FXML
    private TableView<MemoryProperty> memoryTable;
    @FXML
    private TableColumn<MemoryProperty, String> propertyColumn;
    @FXML
    private TableColumn<MemoryProperty, String> valueColumn;
    @FXML
    private Button showUsageButton;

    private ObservableList<MemoryProperty> memoryData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        showUsageButton.setText(bundle.getString("memory.show.usage"));

        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        memoryTable.setItems(memoryData);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateMemoryInfo();
            }
        };
        timer.start();
    }

    private void updateMemoryInfo() {
        MemoryInfo info = MemoryMonitor.getMemoryInfo();
        memoryData.clear();
        memoryData.add(new MemoryProperty("Total Memory", String.format("%.2f GB", info.getTotalMemory() / 1_073_741_824.0)));
        memoryData.add(new MemoryProperty("Used Memory", String.format("%.2f GB", info.getUsedMemory() / 1_073_741_824.0)));
        memoryData.add(new MemoryProperty("Free Memory", String.format("%.2f GB", info.getFreeMemory() / 1_073_741_824.0)));
        memoryData.add(new MemoryProperty("Memory Type", info.getMemoryType()));
        memoryData.add(new MemoryProperty("Module Count", String.valueOf(info.getModuleCount())));
        memoryData.add(new MemoryProperty("Memory Frequency", String.format("%.2f MHz", info.getMemoryFrequency() / 1_000_000.0)));
    }

    @FXML
    private void showUsageWindow() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/memory_usage.fxml"), bundle);
        Scene scene = new Scene(loader.load(), 300, 250);
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("memory.usage"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static class MemoryProperty {
        private final String property;
        private final String value;

        public MemoryProperty(String property, String value) {
            this.property = property;
            this.value = value;
        }

        public String getProperty() { return property; }
        public String getValue() { return value; }
    }
}