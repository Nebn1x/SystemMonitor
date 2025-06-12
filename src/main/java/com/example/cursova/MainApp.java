package com.example.cursova;

import com.example.cursova.config.LanguageManager;
import com.example.cursova.utils.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ResourceBundle;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Logger.logAppStart();
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", LanguageManager.getInstance().getCurrentLocale());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), bundle);
        if (loader.getLocation() == null) {
            throw new IllegalStateException("Cannot find FXML file: /fxml/main.fxml");
        }
        Scene scene = new Scene(loader.load(), 450, 350);
        String cssPath = getClass().getResource("/css/styles.css") != null ? getClass().getResource("/css/styles.css").toExternalForm() : null;
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        } else {
            System.err.println("Warning: Cannot find CSS file: /css/styles.css");
        }
        primaryStage.setTitle(bundle.getString("app.title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        InputStream iconStream = getClass().getResourceAsStream("/icons/app_icon.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("Warning: Cannot find icon file: /icons/app_icon.png");
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}