package com.example.cursova.services.hardware;

import com.example.cursova.models.MemoryInfo;
import com.example.cursova.utils.WmiUtil;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import java.util.List;
import java.util.Map;

public class MemoryMonitor {
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hal = systemInfo.getHardware();
    private static final GlobalMemory memory = hal.getMemory();

    public static MemoryInfo getMemoryInfo() {
        MemoryInfo info = new MemoryInfo();
        List<Map<WmiUtil.MemoryProperties, String>> wmiData = WmiUtil.getMemoryDetails();

        info.setTotalMemory(memory.getTotal());
        info.setUsedMemory(memory.getTotal() - memory.getAvailable());
        info.setFreeMemory(memory.getAvailable());

        List<PhysicalMemory> modules = memory.getPhysicalMemory();
        info.setModuleCount(modules.size());
        String memoryType = wmiData.isEmpty() ? (modules.isEmpty() ? "N/A" : modules.get(0).getMemoryType()) : wmiData.get(0).get(WmiUtil.MemoryProperties.MemoryType);
        info.setMemoryType(memoryType);

        long frequency = wmiData.isEmpty() ? (modules.isEmpty() ? 0 : modules.get(0).getClockSpeed()) : Long.parseLong(wmiData.get(0).getOrDefault(WmiUtil.MemoryProperties.ConfiguredClockSpeed, "0"));
        info.setMemoryFrequency(frequency);

        return info;
    }
}