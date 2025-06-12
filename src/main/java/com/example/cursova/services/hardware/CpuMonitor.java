package com.example.cursova.services.hardware;

import com.example.cursova.models.CpuInfo;
import com.example.cursova.utils.WmiUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import java.util.List;
import java.util.Map;

public class CpuMonitor {
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hal = systemInfo.getHardware();
    private static final CentralProcessor cpu = hal.getProcessor();

    public static CpuInfo getCpuInfo() {
        CpuInfo info = new CpuInfo();
        CentralProcessor.ProcessorIdentifier processorId = cpu.getProcessorIdentifier();
        Map<WmiUtil.CpuProperties, String> wmiData = WmiUtil.getCpuDetails();

        info.setName(wmiData.getOrDefault(WmiUtil.CpuProperties.Name, processorId.getName()));
        info.setArchitecture(processorId.isCpu64bit() ? "x64" : "x86");
        info.setPhysicalCores(cpu.getPhysicalProcessorCount());
        info.setLogicalCores(cpu.getLogicalProcessorCount());

        long[] frequencies = cpu.getCurrentFreq();
        if (frequencies != null && frequencies.length > 0) {
            info.setCurrentFrequency(frequencies[0] / 1_000_000_000.0);
        } else {
            info.setCurrentFrequency(Double.parseDouble(wmiData.getOrDefault(WmiUtil.CpuProperties.CurrentClockSpeed, "0")) / 1000.0);
        }

        info.setCacheL1("256 KB");
        info.setCacheL2("512 KB");
        info.setCacheL3(wmiData.getOrDefault(WmiUtil.CpuProperties.L3CacheSize, "32 MB"));

        info.setSocket(wmiData.getOrDefault(WmiUtil.CpuProperties.SocketDesignation, "Socket " + processorId.getProcessorID()));
        info.setProcess(parseProcessFromName(wmiData.getOrDefault(WmiUtil.CpuProperties.Name, processorId.getName())));
        info.setVoltage(0.875);

        info.setFamily(wmiData.getOrDefault(WmiUtil.CpuProperties.Family, processorId.getFamily()));
        info.setModel(wmiData.getOrDefault(WmiUtil.CpuProperties.ProcessorId, processorId.getModel()));
        info.setStepping(wmiData.getOrDefault(WmiUtil.CpuProperties.Stepping, processorId.getStepping()));

        StringBuilder instructions = new StringBuilder();
        List<String> features = cpu.getFeatureFlags();
        if (!features.isEmpty()) {
            instructions.append(String.join(", ", features));
        } else {
            instructions.append("MMX, SSE, SSE2, SSE3, SSSE3, SSE4.1, SSE4.2, AVX, AVX2");
        }
        info.setInstructions(instructions.toString());

        return info;
    }

    public static double[] getCpuLoad() {
        return cpu.getProcessorCpuLoadBetweenTicks(cpu.getProcessorCpuLoadTicks());
    }

    private static String parseProcessFromName(String name) {
        if (name.contains("7nm")) return "7 nm";
        if (name.contains("10nm")) return "10 nm";
        return "N/A";
    }
}