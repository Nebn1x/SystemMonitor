package com.example.cursova.controllers;

import com.example.cursova.config.AppConfig;
import com.example.cursova.config.LanguageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab cpuTab, gpuTab, memoryTab, motherboardTab, storageTab, settingsTab, helpTab;

    private ResourceBundle bundle;
    private CpuController cpuController;
    private GpuController gpuController;
    private MemoryController memoryController;
    private MotherboardController motherboardController;
    private StorageController storageController;
    private SettingsController settingsController;
    private HelpController helpController;

    @FXML
    private void initialize() throws IOException {
        bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());

        if (AppConfig.isShowCpu()) loadTabContent(cpuTab, "/fxml/cpu_tab.fxml", CpuController.class);
        if (AppConfig.isShowGpu()) loadTabContent(gpuTab, "/fxml/gpu_tab.fxml", GpuController.class);
        if (AppConfig.isShowMemory()) loadTabContent(memoryTab, "/fxml/memory_tab.fxml", MemoryController.class);
        if (AppConfig.isShowMotherboard()) loadTabContent(motherboardTab, "/fxml/motherboard_tab.fxml", MotherboardController.class);
        if (AppConfig.isShowStorage()) loadTabContent(storageTab, "/fxml/storage_tab.fxml", StorageController.class);
        loadTabContent(settingsTab, "/fxml/settings_tab.fxml", SettingsController.class);
        loadTabContent(helpTab, "/fxml/help_tab.fxml", HelpController.class);
        updateTabTitles();
    }

    private <T> void loadTabContent(Tab tab, String fxmlPath, Class<T> controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), bundle);
        if (loader.getLocation() == null) {
            throw new IllegalStateException("Cannot find FXML file: " + fxmlPath);
        }
        tab.setContent(loader.load());
        Object controller = loader.getController();
        if (controllerClass.isInstance(controller)) {
            if (controller instanceof CpuController) cpuController = (CpuController) controller;
            else if (controller instanceof GpuController) gpuController = (GpuController) controller;
            else if (controller instanceof MemoryController) memoryController = (MemoryController) controller;
            else if (controller instanceof MotherboardController) motherboardController = (MotherboardController) controller;
            else if (controller instanceof StorageController) storageController = (StorageController) controller;
            else if (controller instanceof SettingsController) {
                settingsController = (SettingsController) controller;
                settingsController.setMainController(this);
            }
            else if (controller instanceof HelpController) helpController = (HelpController) controller;
        }
    }

    private void updateTabTitles() {
        if (AppConfig.isShowCpu()) cpuTab.setText(bundle.getString("tab.cpu"));
        if (AppConfig.isShowGpu()) gpuTab.setText(bundle.getString("tab.gpu"));
        if (AppConfig.isShowMemory()) memoryTab.setText(bundle.getString("tab.memory"));
        if (AppConfig.isShowMotherboard()) motherboardTab.setText(bundle.getString("tab.motherboard"));
        if (AppConfig.isShowStorage()) storageTab.setText(bundle.getString("tab.storage"));
        settingsTab.setText(bundle.getString("tab.settings"));
        helpTab.setText(bundle.getString("tab.help"));
    }

    public void updateLanguage() throws IOException {
        bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        updateTabTitles();
        if (AppConfig.isShowCpu()) reloadTabContent(cpuTab, "/fxml/cpu_tab.fxml", CpuController.class);
        if (AppConfig.isShowGpu()) reloadTabContent(gpuTab, "/fxml/gpu_tab.fxml", GpuController.class);
        if (AppConfig.isShowMemory()) reloadTabContent(memoryTab, "/fxml/memory_tab.fxml", MemoryController.class);
        if (AppConfig.isShowMotherboard()) reloadTabContent(motherboardTab, "/fxml/motherboard_tab.fxml", MotherboardController.class);
        if (AppConfig.isShowStorage()) reloadTabContent(storageTab, "/fxml/storage_tab.fxml", StorageController.class);
        reloadTabContent(settingsTab, "/fxml/settings_tab.fxml", SettingsController.class);
        reloadTabContent(helpTab, "/fxml/help_tab.fxml", HelpController.class);
    }

    private <T> void reloadTabContent(Tab tab, String fxmlPath, Class<T> controllerClass) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), bundle);
        tab.setContent(loader.load());
        Object controller = loader.getController();
        if (controllerClass.isInstance(controller)) {
            if (controller instanceof CpuController) cpuController = (CpuController) controller;
            else if (controller instanceof GpuController) gpuController = (GpuController) controller;
            else if (controller instanceof MemoryController) memoryController = (MemoryController) controller;
            else if (controller instanceof MotherboardController) motherboardController = (MotherboardController) controller;
            else if (controller instanceof StorageController) storageController = (StorageController) controller;
            else if (controller instanceof SettingsController) {
                settingsController = (SettingsController) controller;
                settingsController.setMainController(this);
            }
            else if (controller instanceof HelpController) helpController = (HelpController) controller;
        }
    }
}