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

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public double getHomework() { return homework; }
        public void setHomework(double homework) { this.homework = homework; }

        public double getQuiz() { return quiz; }
        public void setQuiz(double quiz) { this.quiz = quiz; }

        public double getTest() { return test; }
        public void setTest(double test) { this.test = test; }

        public double getExam() { return exam; }
        public void setExam(double exam) { this.exam = exam; }

        public double getCredits() { return credits; }
        public void setCredits(double credits) { this.credits = credits; }

        public double getFinalScore() { return finalScore; }
        public String getLetterGrade() { return letterGrade; }
        public double getGpaValue() { return gpaValue; }

        public void calculateFinalGrade() {
            finalScore = (homework * 0.3) + (quiz * 0.2) + (test * 0.3) + (exam * 0.2);
            if(finalScore >= 97) {
                letterGrade = "A+";
                gpaValue = 4.5;
            }
            else if(finalScore >= 93) {
                letterGrade = "A";
                gpaValue = 4.3;
            }
            else if(finalScore >= 90) {
                letterGrade = "A-";
                gpaValue = 4.0;
            }
            else if (finalScore >= 87){
                letterGrade = "B+";
                gpaValue = 3.5;
            }
            else if(finalScore >= 83) {
                letterGrade = "B";
                gpaValue = 3.0;
            }
        }
    }
}
