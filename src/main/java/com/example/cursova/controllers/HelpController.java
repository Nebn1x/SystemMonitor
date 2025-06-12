package com.example.cursova.controllers;

import com.example.cursova.config.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class HelpController {
    @FXML
    private Label helpLabel;

    @FXML
    private void initialize() {
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        helpLabel.setText(bundle.getString("help.text"));
    }
}