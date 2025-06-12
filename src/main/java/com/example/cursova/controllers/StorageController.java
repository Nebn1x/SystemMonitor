package com.example.cursova.controllers;

import com.example.cursova.models.StorageInfo;
import com.example.cursova.services.hardware.StorageMonitor;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StorageController {
    @FXML
    private TableView<StorageInfo> storageTable;
    @FXML
    private TableColumn<StorageInfo, String> typeColumn;
    @FXML
    private TableColumn<StorageInfo, Double> totalSpaceColumn;
    @FXML
    private TableColumn<StorageInfo, Double> usedSpaceColumn;
    @FXML
    private TableColumn<StorageInfo, Double> freeSpaceColumn;

    @FXML
    private void initialize() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalSpaceColumn.setCellValueFactory(new PropertyValueFactory<>("totalSpace"));
        usedSpaceColumn.setCellValueFactory(new PropertyValueFactory<>("usedSpace"));
        freeSpaceColumn.setCellValueFactory(new PropertyValueFactory<>("freeSpace"));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateStorageInfo();
            }
        };
        timer.start();
    }

    private void updateStorageInfo() {
        List<StorageInfo> storageList = StorageMonitor.getStorageInfo();
        storageTable.getItems().clear();
        for (StorageInfo info : storageList) {
            double totalGB = info.getTotalSpace() / 1_073_741_824.0;
            double usedGB = info.getUsedSpace() / 1_073_741_824.0;
            double freeGB = totalGB - usedGB;
            info.setTotalSpace(totalGB);
            info.setUsedSpace(usedGB);
            info.setFreeSpace(freeGB);
            storageTable.getItems().add(info);
        }
    }
}