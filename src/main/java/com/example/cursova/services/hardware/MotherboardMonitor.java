package com.example.cursova.services.hardware;

import com.example.cursova.models.MotherboardInfo;
import com.example.cursova.utils.WmiUtil;
import oshi.SystemInfo;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import java.util.Map;

public class MotherboardMonitor {
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hal = systemInfo.getHardware();

    public static MotherboardInfo getMotherboardInfo() {
        MotherboardInfo info = new MotherboardInfo();
        ComputerSystem computerSystem = hal.getComputerSystem();
        Map<WmiUtil.MotherboardProperties, String> wmiData = WmiUtil.getMotherboardDetails();

        info.setManufacturer(wmiData.getOrDefault(WmiUtil.MotherboardProperties.Manufacturer, computerSystem.getManufacturer()));
        info.setModel(wmiData.getOrDefault(WmiUtil.MotherboardProperties.Product, computerSystem.getModel()));
        info.setChipset("N/A"); // WMI не надає чіпсет
        info.setSouthbridge("N/A"); // WMI не завжди надає
        info.setBios(computerSystem.getFirmware().getVersion());
        return info;
    }
}