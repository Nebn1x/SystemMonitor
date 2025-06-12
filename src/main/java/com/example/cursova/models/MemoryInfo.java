package com.example.cursova.models;

public class MemoryInfo {
    private long totalMemory;
    private long usedMemory;
    private long freeMemory;
    private String memoryType;
    private int moduleCount;
    private long memoryFrequency;

    public long getTotalMemory() { return totalMemory; }
    public void setTotalMemory(long totalMemory) { this.totalMemory = totalMemory; }
    public long getUsedMemory() { return usedMemory; }
    public void setUsedMemory(long usedMemory) { this.usedMemory = usedMemory; }
    public long getFreeMemory() { return freeMemory; }
    public void setFreeMemory(long freeMemory) { this.freeMemory = freeMemory; }
    public String getMemoryType() { return memoryType; }
    public void setMemoryType(String memoryType) { this.memoryType = memoryType; }
    public int getModuleCount() { return moduleCount; }
    public void setModuleCount(int moduleCount) { this.moduleCount = moduleCount; }
    public long getMemoryFrequency() { return memoryFrequency; }
    public void setMemoryFrequency(long memoryFrequency) { this.memoryFrequency = memoryFrequency; }
}