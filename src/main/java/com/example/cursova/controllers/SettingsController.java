package com.example.cursova.controllers;

import com.example.cursova.config.AppConfig;
import com.example.cursova.config.LanguageManager;
import com.example.cursova.models.CpuInfo;
import com.example.cursova.models.GpuInfo;
import com.example.cursova.models.MemoryInfo;
import com.example.cursova.models.MotherboardInfo;
import com.example.cursova.models.StorageInfo;
import com.example.cursova.services.hardware.*;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController {
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private Button applyButton;
    @FXML
    private Button exportJsonButton;
    @FXML
    private Button exportTxtButton;

    private ResourceBundle bundle;
    private MainController mainController;

    @FXML
    private void initialize() {
        bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        languageChoiceBox.getItems().addAll(
                bundle.getString("settings.language.uk"),
                bundle.getString("settings.language.en")
        );
        languageChoiceBox.setValue(
                LanguageManager.getInstance().getCurrentLocale().getLanguage().equals("uk") ?
                        bundle.getString("settings.language.uk") :
                        bundle.getString("settings.language.en")
        );

        applyButton.setText(bundle.getString("settings.apply"));
        exportJsonButton.setText("Export to JSON");
        exportTxtButton.setText("Export to TXT");
    }

    @FXML
    private void handleApply() {
        String selectedLanguage = languageChoiceBox.getValue();
        String languageCode = selectedLanguage.equals(bundle.getString("settings.language.uk")) ? "uk" : "en";
        LanguageManager.getInstance().setLanguage(languageCode);

        try {
            if (mainController != null) {
                mainController.updateLanguage();
            }
        } catch (Exception e) {
            System.err.println("Помилка оновлення мови: " + e.getMessage());
        }
    }

    @FXML
    private void exportToJson() {
        try {
            Gson gson = new Gson();
            HardwareSummary summary = getHardwareSummary();
            String json = gson.toJson(summary);
            FileWriter writer = new FileWriter("hardware_info.json");
            writer.write(json);
            writer.close();
            System.out.println("Exported to hardware_info.json");
        } catch (IOException e) {
            System.err.println("Error exporting to JSON: " + e.getMessage());
        }
    }

    @FXML
    private void exportToTxt() {
        try {
            String txt = getHardwareInfoAsString();
            FileWriter writer = new FileWriter("hardware_info.txt");
            writer.write(txt);
            writer.close();
            System.out.println("Exported to hardware_info.txt");
        } catch (IOException e) {
            System.err.println("Error exporting to TXT: " + e.getMessage());
        }
    }

    private HardwareSummary getHardwareSummary() {
        HardwareSummary summary = new HardwareSummary();

        // CPU
        CpuInfo cpu = CpuMonitor.getCpuInfo();
        CpuSummary cpuSummary = new CpuSummary();
        cpuSummary.name = cpu.getName();
        cpuSummary.architecture = cpu.getArchitecture();
        cpuSummary.physicalCores = cpu.getPhysicalCores();
        cpuSummary.logicalCores = cpu.getLogicalCores();
        cpuSummary.currentFrequency = cpu.getCurrentFrequency();
        cpuSummary.cacheL1 = cpu.getCacheL1();
        cpuSummary.cacheL2 = cpu.getCacheL2();
        cpuSummary.cacheL3 = cpu.getCacheL3();
        cpuSummary.socket = cpu.getSocket();
        cpuSummary.process = cpu.getProcess();
        cpuSummary.voltage = cpu.getVoltage();
        cpuSummary.family = cpu.getFamily();
        cpuSummary.model = cpu.getModel();
        cpuSummary.stepping = cpu.getStepping();
        cpuSummary.instructions = cpu.getInstructions();
        summary.cpuInfo = cpuSummary;

        // GPU
        GpuInfo gpu = GpuMonitor.getGpuInfo();
        GpuSummary gpuSummary = new GpuSummary();
        gpuSummary.model = gpu.getModel();
        gpuSummary.driver = gpu.getDriver();
        double memoryGB = gpu.getMemory() / 1_073_741_824.0;
        gpuSummary.memory = memoryGB > 0 ? memoryGB : 0;
        gpuSummary.coreClock = gpu.getCoreClock();
        gpuSummary.memoryClock = gpu.getMemoryClock();
        summary.gpuInfo = gpuSummary;

        // Memory
        MemoryInfo memory = MemoryMonitor.getMemoryInfo();
        MemorySummary memorySummary = new MemorySummary();
        memorySummary.totalMemory = memory.getTotalMemory() / 1_073_741_824.0;
        memorySummary.usedMemory = memory.getUsedMemory() / 1_073_741_824.0;
        memorySummary.freeMemory = memory.getFreeMemory() / 1_073_741_824.0;
        memorySummary.memoryType = memory.getMemoryType();
        memorySummary.moduleCount = memory.getModuleCount();
        memorySummary.memoryFrequency = memory.getMemoryFrequency();
        summary.memoryInfo = memorySummary;

        // Motherboard
        MotherboardInfo motherboard = MotherboardMonitor.getMotherboardInfo();
        MotherboardSummary motherboardSummary = new MotherboardSummary();
        motherboardSummary.manufacturer = motherboard.getManufacturer();
        motherboardSummary.model = motherboard.getModel();
        motherboardSummary.chipset = motherboard.getChipset();
        motherboardSummary.southbridge = motherboard.getSouthbridge();
        motherboardSummary.bios = motherboard.getBios();
        summary.motherboardInfo = motherboardSummary;

        // Storage
        List<StorageInfo> storageList = StorageMonitor.getStorageInfo();
        List<StorageSummary> storageSummaries = new ArrayList<>();
        for (StorageInfo storage : storageList) {
            StorageSummary storageSummary = new StorageSummary();
            storageSummary.type = storage.getType();
            storageSummary.totalSpace = storage.getTotalSpace() / 1_073_741_824.0;
            storageSummary.usedSpace = storage.getUsedSpace() / 1_073_741_824.0;
            storageSummary.freeSpace = storage.getFreeSpace() / 1_073_741_824.0;
            storageSummaries.add(storageSummary);
        }
        summary.storageInfo = storageSummaries;

        return summary;
    }

    private String getHardwareInfoAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hardware Information:\n\n");

        // CPU
        CpuInfo cpu = CpuMonitor.getCpuInfo();
        sb.append("CPU:\n");
        sb.append("Name - ").append(cpu.getName()).append("\n");
        sb.append("Architecture - ").append(cpu.getArchitecture()).append("\n");
        sb.append("Physical Cores - ").append(cpu.getPhysicalCores()).append("\n");
        sb.append("Logical Cores - ").append(cpu.getLogicalCores()).append("\n");
        sb.append("Current Frequency - ").append(cpu.getCurrentFrequency()).append(" GHz\n");
        sb.append("Cache L1 - ").append(cpu.getCacheL1()).append("\n");
        sb.append("Cache L2 - ").append(cpu.getCacheL2()).append("\n");
        sb.append("Cache L3 - ").append(cpu.getCacheL3()).append("\n");
        sb.append("Socket - ").append(cpu.getSocket()).append("\n");
        sb.append("Process - ").append(cpu.getProcess()).append("\n");
        sb.append("Voltage - ").append(cpu.getVoltage()).append(" V\n");
        sb.append("Family - ").append(cpu.getFamily()).append("\n");
        sb.append("Model - ").append(cpu.getModel()).append("\n");
        sb.append("Stepping - ").append(cpu.getStepping()).append("\n");
        sb.append("Instructions - ").append(cpu.getInstructions()).append("\n\n");

        // GPU
        GpuInfo gpu = GpuMonitor.getGpuInfo();
        sb.append("GPU:\n");
        sb.append("Model - ").append(gpu.getModel()).append("\n");
        sb.append("Driver - ").append(gpu.getDriver()).append("\n");
        double memoryGB = gpu.getMemory() / 1_073_741_824.0;
        sb.append("Memory - ").append(memoryGB > 0 ? String.format("%.2f GB", memoryGB) : "N/A").append("\n");
        sb.append("Core Clock - ").append(gpu.getCoreClock()).append(" MHz\n");
        sb.append("Memory Clock - ").append(gpu.getMemoryClock()).append(" MHz\n\n");

        // Memory
        MemoryInfo memory = MemoryMonitor.getMemoryInfo();
        sb.append("Memory:\n");
        sb.append("Total Memory - ").append(String.format("%.2f GB", memory.getTotalMemory() / 1_073_741_824.0)).append("\n");
        sb.append("Used Memory - ").append(String.format("%.2f GB", memory.getUsedMemory() / 1_073_741_824.0)).append("\n");
        sb.append("Free Memory - ").append(String.format("%.2f GB", memory.getFreeMemory() / 1_073_741_824.0)).append("\n");
        sb.append("Memory Type - ").append(memory.getMemoryType()).append("\n");
        sb.append("Module Count - ").append(memory.getModuleCount()).append("\n");
        sb.append("Memory Frequency - ").append(memory.getMemoryFrequency()).append(" MHz\n\n");

        // Motherboard
        MotherboardInfo motherboard = MotherboardMonitor.getMotherboardInfo();
        sb.append("Motherboard:\n");
        sb.append("Manufacturer - ").append(motherboard.getManufacturer()).append("\n");
        sb.append("Model - ").append(motherboard.getModel()).append("\n");
        sb.append("Chipset - ").append(motherboard.getChipset()).append("\n");
        sb.append("Southbridge - ").append(motherboard.getSouthbridge()).append("\n");
        sb.append("BIOS - ").append(motherboard.getBios()).append("\n\n");

        // Storage
        List<StorageInfo> storageList = StorageMonitor.getStorageInfo();
        sb.append("Storage:\n");
        for (int i = 0; i < storageList.size(); i++) {
            StorageInfo storage = storageList.get(i);
            sb.append("Storage ").append(i + 1).append(":\n");
            sb.append("Type - ").append(storage.getType()).append("\n");
            sb.append("Total Space - ").append(String.format("%.2f GB", storage.getTotalSpace() / 1_073_741_824.0)).append("\n");
            sb.append("Used Space - ").append(String.format("%.2f GB", storage.getUsedSpace() / 1_073_741_824.0)).append("\n");
            sb.append("Free Space - ").append(String.format("%.2f GB", storage.getFreeSpace() / 1_073_741_824.0)).append("\n\n");
        }

        return sb.toString();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private static class HardwareSummary {
        public CpuSummary cpuInfo;
        public GpuSummary gpuInfo;
        public MemorySummary memoryInfo;
        public MotherboardSummary motherboardInfo;
        public List<StorageSummary> storageInfo;
    }

    private static class CpuSummary {
        public String name;
        public String architecture;
        public int physicalCores;
        public int logicalCores;
        public double currentFrequency;
        public String cacheL1;
        public String cacheL2;
        public String cacheL3;
        public String socket;
        public String process;
        public double voltage;
        public String family;
        public String model;
        public String stepping;
        public String instructions;
    }

    private static class GpuSummary {
        public String model;
        public String driver;
        public double memory;
        public long coreClock;
        public long memoryClock;
    }

    private static class MemorySummary {
        public double totalMemory;
        public double usedMemory;
        public double freeMemory;
        public String memoryType;
        public int moduleCount;
        public long memoryFrequency;
    }

    private static class MotherboardSummary {
        public String manufacturer;
        public String model;
        public String chipset;
        public String southbridge;
        public String bios;
    }

    private static class StorageSummary {
        public String type;
        public double totalSpace;
        public double usedSpace;
        public double freeSpace;
    }
}