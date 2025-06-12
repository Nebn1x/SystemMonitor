package com.example.cursova.models;

public class CpuInfo {
    private String name;
    private String architecture;
    private int physicalCores;
    private int logicalCores;
    private double currentFrequency;
    private String cacheL1;
    private String cacheL2;
    private String cacheL3;
    private String socket;
    private String process;
    private double voltage;
    private String family;
    private String model;
    private String stepping;
    private String instructions;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getArchitecture() { return architecture; }
    public void setArchitecture(String architecture) { this.architecture = architecture; }
    public int getPhysicalCores() { return physicalCores; }
    public void setPhysicalCores(int physicalCores) { this.physicalCores = physicalCores; }
    public int getLogicalCores() { return logicalCores; }
    public void setLogicalCores(int logicalCores) { this.logicalCores = logicalCores; }
    public double getCurrentFrequency() { return currentFrequency; }
    public void setCurrentFrequency(double currentFrequency) { this.currentFrequency = currentFrequency; }
    public String getCacheL1() { return cacheL1; }
    public void setCacheL1(String cacheL1) { this.cacheL1 = cacheL1; }
    public String getCacheL2() { return cacheL2; }
    public void setCacheL2(String cacheL2) { this.cacheL2 = cacheL2; }
    public String getCacheL3() { return cacheL3; }
    public void setCacheL3(String cacheL3) { this.cacheL3 = cacheL3; }
    public String getSocket() { return socket; }
    public void setSocket(String socket) { this.socket = socket; }
    public String getProcess() { return process; }
    public void setProcess(String process) { this.process = process; }
    public double getVoltage() { return voltage; }
    public void setVoltage(double voltage) { this.voltage = voltage; }
    public String getFamily() { return family; }
    public void setFamily(String family) { this.family = family; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getStepping() { return stepping; }
    public void setStepping(String stepping) { this.stepping = stepping; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}