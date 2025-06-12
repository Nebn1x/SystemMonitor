package com.example.cursova.services.hardware;

import com.example.cursova.models.GpuInfo;
import com.example.cursova.utils.Logger;
import com.example.cursova.utils.WmiUtil;
import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import java.util.List;
import java.util.Map;

public class GpuMonitor {
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hal = systemInfo.getHardware();

    public static GpuInfo getGpuInfo() {
        GpuInfo info = new GpuInfo();
        List<GraphicsCard> gpus = hal.getGraphicsCards();
        Map<WmiUtil.GpuProperties, String> wmiData = WmiUtil.getGpuDetails();

        if (!gpus.isEmpty()) {
            GraphicsCard gpu = gpus.get(0);
            info.setModel(wmiData.getOrDefault(WmiUtil.GpuProperties.Name, gpu.getName()));
            info.setDriver(wmiData.getOrDefault(WmiUtil.GpuProperties.DriverVersion, gpu.getVersionInfo()));
            long memoryValue = Long.parseLong(wmiData.getOrDefault(WmiUtil.GpuProperties.AdapterRAM, String.valueOf(gpu.getVRam())));
            info.setMemory(memoryValue > 0 ? memoryValue : 0);
            info.setCoreClock(1417);
            info.setMemoryClock(7500);
        } else {
            info.setModel(wmiData.getOrDefault(WmiUtil.GpuProperties.Name, "N/A"));
            info.setDriver(wmiData.getOrDefault(WmiUtil.GpuProperties.DriverVersion, "N/A"));
            long memoryValue = Long.parseLong(wmiData.getOrDefault(WmiUtil.GpuProperties.AdapterRAM, "0"));
            info.setMemory(memoryValue > 0 ? memoryValue : 0);
            info.setCoreClock(0);
            info.setMemoryClock(0);
        }
        return info;
    }
}