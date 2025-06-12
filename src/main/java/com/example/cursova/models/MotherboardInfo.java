package com.example.cursova.models;

public class MotherboardInfo {
    private String manufacturer;
    private String model;
    private String chipset;
    private String southbridge;
    private String bios;

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getChipset() { return chipset; }
    public void setChipset(String chipset) { this.chipset = chipset; }
    public String getSouthbridge() { return southbridge; }
    public void setSouthbridge(String southbridge) { this.southbridge = southbridge; }
    public String getBios() { return bios; }
    public void setBios(String bios) { this.bios = bios; }
}