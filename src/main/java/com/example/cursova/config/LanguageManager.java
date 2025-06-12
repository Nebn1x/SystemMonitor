package com.example.cursova.config;

import java.util.Locale;
import java.util.prefs.Preferences;

public class LanguageManager {
    private static final String LANGUAGE_PREF_KEY = "language";
    private static LanguageManager instance;
    private Locale currentLocale;
    private Preferences prefs;

    private LanguageManager() {
        prefs = Preferences.userNodeForPackage(LanguageManager.class);
        String language = prefs.get(LANGUAGE_PREF_KEY, "en");
        currentLocale = new Locale(language);
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setLanguage(String language) {
        currentLocale = new Locale(language);
        prefs.put(LANGUAGE_PREF_KEY, language);
    }
}