package com.example.cursova.models;

public class GpuInfo {
    private String model;
    private String driver;
    private long memory;
    private long coreClock;
    private long memoryClock;

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }
    public long getMemory() { return memory; }
    public void setMemory(long memory) { this.memory = memory; }
    public long getCoreClock() { return coreClock; }
    public void setCoreClock(long coreClock) { this.coreClock = coreClock; }
    public long getMemoryClock() { return memoryClock; }
    public void setMemoryClock(long memoryClock) { this.memoryClock = memoryClock; }
}