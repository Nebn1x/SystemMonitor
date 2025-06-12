package com.example.cursova.models;

public class StorageInfo {
    private String type;
    private double totalSpace;
    private double usedSpace;
    private double freeSpace;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getTotalSpace() { return totalSpace; }
    public void setTotalSpace(double totalSpace) { this.totalSpace = totalSpace; }
    public double getUsedSpace() { return usedSpace; }
    public void setUsedSpace(double usedSpace) { this.usedSpace = usedSpace; }
    public double getFreeSpace() { return freeSpace; }
    public void setFreeSpace(double freeSpace) { this.freeSpace = freeSpace; }

    @Override
    public String toString() {
        double totalGB = totalSpace / 1_073_741_824.0;
        double usedGB = usedSpace / 1_073_741_824.0;
        double freeGB = freeSpace / 1_073_741_824.0;
        return String.format("Type: %s, Total: %.2f GB, Used: %.2f GB, Free: %.2f GB", type, totalGB, usedGB, freeGB);
    }
}