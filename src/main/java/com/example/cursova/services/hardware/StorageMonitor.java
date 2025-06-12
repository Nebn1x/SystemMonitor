package com.example.cursova.services.hardware;

import com.example.cursova.models.StorageInfo;
import com.example.cursova.utils.Logger;
import com.example.cursova.utils.WmiUtil;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StorageMonitor {
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hal = systemInfo.getHardware();

    // Define enum for Win32_LogicalDisk properties
    public enum LogicalDiskProperties {
        DeviceID,
        DriveType,
        VolumeName,
        FileSystem,
        Size,
        FreeSpace
    }

    public static List<StorageInfo> getStorageInfo() {
        List<StorageInfo> storageList = new ArrayList<>();

        try {
            // Use WMI to get logical disk information
            WmiQuery<LogicalDiskProperties> logicalDiskQuery = new WmiQuery<>("ROOT\\CIMV2", "Win32_LogicalDisk WHERE DriveType=3", LogicalDiskProperties.class);
            WmiResult<LogicalDiskProperties> logicalDiskResult = logicalDiskQuery.execute();

            for (int i = 0

                 ; i < logicalDiskResult.getResultCount(); i++) {
                StorageInfo info = new StorageInfo();
                String driveType = logicalDiskResult.getValue(LogicalDiskProperties.DriveType, i).toString();
                info.setType(driveType.equals("3") ? "HDD/SSD" : "Unknown");
                info.setTotalSpace(Double.parseDouble(logicalDiskResult.getValue(LogicalDiskProperties.Size, i).toString()));
                info.setUsedSpace(Double.parseDouble(logicalDiskResult.getValue(LogicalDiskProperties.Size, i).toString()) -
                        Double.parseDouble(logicalDiskResult.getValue(LogicalDiskProperties.FreeSpace, i).toString()));
                storageList.add(info);
            }
        } catch (Exception e) {
            Logger.logError("Error retrieving disk data via WMI: " + e.getMessage());
            // Fallback to OSHI's OSFileStore
            try {
                List<OSFileStore> fileStores = systemInfo.getOperatingSystem().getFileSystem().getFileStores();
                for (OSFileStore fs : fileStores) {
                    StorageInfo info = new StorageInfo();
                    info.setType(fs.getType());
                    info.setTotalSpace(fs.getTotalSpace());
                    info.setUsedSpace(fs.getTotalSpace() - fs.getUsableSpace());
                    storageList.add(info);
                }
            } catch (Exception ex) {
                Logger.logError("Error retrieving disk data via OSHI: " + ex.getMessage());
            }
        }

        if (storageList.isEmpty()) {
            Logger.logError("Failed to retrieve disk data from both WMI and OSHI");
        } else {
            Logger.log("Retrieved data for " + storageList.size() + " disk(s)");
        }

        return storageList;
    }
}