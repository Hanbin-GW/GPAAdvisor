package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainPageController extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-page.fxml")));
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("GPA Advisor");
        stage.setScene(scene);
        stage.show();
    }
}
