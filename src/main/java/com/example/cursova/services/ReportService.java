package com.example.cursova.services;

import com.example.cursova.models.*;
import com.example.cursova.services.hardware.*;
import com.example.cursova.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public static void exportToTxt(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("System Report - " + LocalDateTime.now() + "\n\n");

            CpuInfo cpu = CpuMonitor.getCpuInfo();
            writer.write("CPU:\n");
            writer.write("Model: " + cpu.getName() + "\n");
            writer.write("Architecture: " + cpu.getArchitecture() + "\n");
            writer.write("Cores/Threads: " + cpu.getPhysicalCores() + "/" + cpu.getLogicalCores() + "\n");
            writer.write("Frequency: " + cpu.getCurrentFrequency() + " GHz\n\n");

            MemoryInfo memory = MemoryMonitor.getMemoryInfo();
            writer.write("Memory:\n");
            writer.write("Total: " + (memory.getTotalMemory() / 1_073_741_824.0) + " GB\n");
            writer.write("Used: " + (memory.getUsedMemory() / 1_073_741_824.0) + " GB\n");
            writer.write("Type: " + memory.getMemoryType() + "\n");
            writer.write("Modules: " + memory.getModuleCount() + "\n\n");

            GpuInfo gpu = GpuMonitor.getGpuInfo();
            writer.write("GPU:\n");
            writer.write("Model: " + gpu.getModel() + "\n");
            writer.write("Driver: " + gpu.getDriver() + "\n");
            writer.write("Memory: " + (gpu.getMemory() / 1_073_741_824.0) + " GB\n\n");

            MotherboardInfo motherboard = MotherboardMonitor.getMotherboardInfo();
            writer.write("Motherboard:\n");
            writer.write("Manufacturer: " + motherboard.getManufacturer() + "\n");
            writer.write("Model: " + motherboard.getModel() + "\n");
            writer.write("Chipset: " + motherboard.getChipset() + "\n");
            writer.write("BIOS: " + motherboard.getBios() + "\n\n");

            List<StorageInfo> storageList = StorageMonitor.getStorageInfo();
            writer.write("Storage:\n");
            for (int i = 0; i < storageList.size(); i++) {
                StorageInfo storage = storageList.get(i);
                writer.write("Disk " + (i + 1) + ":\n");
                writer.write("Type: " + storage.getType() + "\n");
                writer.write("Total: " + String.format("%.2f GB", storage.getTotalSpace() / 1_073_741_824.0) + "\n");
                writer.write("Used: " + String.format("%.2f GB", storage.getUsedSpace() / 1_073_741_824.0) + "\n\n");
            }

            Logger.logReportExport("TXT", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportToJson(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> report = new HashMap<>();

            report.put("cpu", CpuMonitor.getCpuInfo());
            report.put("memory", MemoryMonitor.getMemoryInfo());
            report.put("gpu", GpuMonitor.getGpuInfo());
            report.put("motherboard", MotherboardMonitor.getMotherboardInfo());
            report.put("storage", StorageMonitor.getStorageInfo());

            mapper.writeValue(new FileWriter(filePath), report);
            Logger.logReportExport("JSON", filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}