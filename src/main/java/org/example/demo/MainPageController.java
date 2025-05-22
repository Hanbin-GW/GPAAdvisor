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

    private static class Subject{
        private String name;
        private double homework;
        private double quiz;
        private double test;
        private double exam;
        private double credits;
        private double finalScore; // FinalScore (Max Score 100)
        private String letterGrade; // Credit (A+, A0 , etc)
        private double gpaValue; // GPA (4.5, 4.3 ,etc)

        public Subject(String name, double credits) {
            this.name = name;
            this.credits = credits;
            this.homework = 0;
            this.quiz = 0;
            this.test = 0;
            this.exam = 0;
            this.finalScore = 0;
            this.letterGrade = "F";
            this.gpaValue = 0.0;
        }
    }
}
