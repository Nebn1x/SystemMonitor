package com.example.cursova.config;

public class AppConfig {
    private static int updateFrequency = 1000;
    private static String language = "en";
    private static boolean showCpu = true;
    private static boolean showGpu = true;
    private static boolean showMemory = true;
    private static boolean showMotherboard = true;
    private static boolean showStorage = true;

    public static int getUpdateFrequency() {
        return updateFrequency;
    }

    public static void setUpdateFrequency(int frequency) {
        updateFrequency = frequency;
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String lang) {
        language = lang;
        LanguageManager.getInstance().setLanguage(lang);
    }

    public static boolean isShowCpu() {
        return showCpu;
    }

    public static void setShowCpu(boolean show) {
        showCpu = show;
    }

    public static boolean isShowGpu() {
        return showGpu;
    }

    public static void setShowGpu(boolean show) {
        showGpu = show;
    }

    public static boolean isShowMemory() {
        return showMemory;
    }

    public static void setShowMemory(boolean show) {
        showMemory = show;
    }

    public static boolean isShowMotherboard() {
        return showMotherboard;
    }

    public static void setShowMotherboard(boolean show) {
        showMotherboard = show;
    }

    public static boolean isShowStorage() {
        return showStorage;
    }

    public static void setShowStorage(boolean show) {
        showStorage = show;
    }
}