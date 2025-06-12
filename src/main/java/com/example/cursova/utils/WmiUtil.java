package com.example.cursova.utils;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WmiUtil {
    public static enum CpuProperties {
        Name,
        SocketDesignation,
        Manufacturer,
        CurrentClockSpeed,
        L3CacheSize,
        Architecture,
        Family,
        Stepping,
        ProcessorId
    }

    public static enum GpuProperties {
        Name,
        AdapterRAM,
        VideoProcessor,
        DriverVersion
    }

    public static enum MemoryProperties {
        MemoryType,
        Speed,
        ConfiguredClockSpeed,
        FormFactor,
        Capacity
    }

    public static enum MotherboardProperties {
        Manufacturer,
        Product,
        Version,
        SerialNumber
    }

    public static <T extends Enum<T>> Map<T, String> queryWmi(String namespace, String wmiClass, Class<T> propertyEnum) {
        WmiQuery<T> query = new WmiQuery<>(namespace, wmiClass, propertyEnum);
        WmiResult<T> result = query.execute();
        Map<T, String> values = new HashMap<>();
        if (result.getResultCount() > 0) {
            for (T prop : propertyEnum.getEnumConstants()) {
                Object value = result.getValue(prop, 0);
                values.put(prop, value != null ? value.toString() : "N/A");
            }
        }
        return values;
    }

    public static Map<CpuProperties, String> getCpuDetails() {
        return queryWmi("ROOT\\CIMV2", "Win32_Processor", CpuProperties.class);
    }

    public static Map<GpuProperties, String> getGpuDetails() {
        return queryWmi("ROOT\\CIMV2", "Win32_VideoController", GpuProperties.class);
    }

    public static List<Map<MemoryProperties, String>> getMemoryDetails() {
        WmiQuery<MemoryProperties> query = new WmiQuery<>("ROOT\\CIMV2", "Win32_PhysicalMemory", MemoryProperties.class);
        WmiResult<MemoryProperties> result = query.execute();
        List<Map<MemoryProperties, String>> modules = new ArrayList<>();
        for (int i = 0; i < result.getResultCount(); i++) {
            Map<MemoryProperties, String> module = new HashMap<>();
            for (MemoryProperties prop : MemoryProperties.values()) {
                Object value = result.getValue(prop, i);
                module.put(prop, value != null ? value.toString() : "N/A");
            }
            modules.add(module);
        }
        return modules;
    }

    public static Map<MotherboardProperties, String> getMotherboardDetails() {
        return queryWmi("ROOT\\CIMV2", "Win32_BaseBoard", MotherboardProperties.class);
    }

    public static enum LogicalDiskProperties {
        DeviceID,
        Size,
        FreeSpace,
        ProviderName
    }

    public static enum DiskDriveProperties {
        Model
    }
}