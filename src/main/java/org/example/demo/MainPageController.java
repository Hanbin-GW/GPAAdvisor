package org.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageController extends Application {

    private static class Subject {
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
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getHomework() {
            return homework;
        }

        public void setHomework(double homework) {
            this.homework = homework;
        }

        public double getQuiz() {
            return quiz;
        }

        public void setQuiz(double quiz) {
            this.quiz = quiz;
        }

        public double getTest() {
            return test;
        }

        public void setTest(double test) {
            this.test = test;
        }

        public double getExam() {
            return exam;
        }

        public void setExam(double exam) {
            this.exam = exam;
        }

        public double getCredits() {
            return credits;
        }

        public void setCredits(double credits) {
            this.credits = credits;
        }

        public double getFinalScore() {
            return finalScore;
        }

        public String getLetterGrade() {
            return letterGrade;
        }

        public double getGpaValue() {
            return gpaValue;
        }

        public void calculateFinalGrade() {
            finalScore = (homework * 0.3) + (quiz * 0.2) + (test * 0.3) + (exam * 0.2);
            if (finalScore >= 97) {
                letterGrade = "A+";
                gpaValue = 4.5;
            } else if (finalScore >= 93) {
                letterGrade = "A0";
                gpaValue = 4.3;
            } else if (finalScore >= 90) {
                letterGrade = "A-";
                gpaValue = 4.0;
            } else if (finalScore >= 87) {
                letterGrade = "B+";
                gpaValue = 3.5;
            } else if (finalScore >= 83) {
                letterGrade = "B0";
                gpaValue = 3.3;
            } else if (finalScore >= 80) {
                letterGrade = "B-";
                gpaValue = 3.0;
            } else if (finalScore >= 77) {
                letterGrade = "C+";
                gpaValue = 2.5;
            } else if (finalScore >= 73) {
                letterGrade = "C0";
                gpaValue = 2.3;
            } else if (finalScore >= 70) {
                letterGrade = "C-";
                gpaValue = 2.0;
            } else {
                letterGrade = "F";
                gpaValue = 0.0;
            }
        }
    }

    // 과목 목록
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private ListView<Subject> subjectListView;
    private TextField nameField, creditsField, homeworkField, quizField, testField, examField;
    private TextField targetGPAField;
    private CheckBox predictionCheckBox;
    private Label resultLabel, predictionLabel, gradeLabel, targetFeedbackLabel;

    // 현재 계산된 GPA 저장
    private double currentGPA = 0.0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GPA Calculator");

        // Main layout
        BorderPane mainLayout = new BorderPane();

        // 왼쪽 패널 - 과목 목록Left Panel - Subject List
        //VBox leftPanel = createLeftPanel();
        //mainLayout.setLeft(leftPanel);

        // Right Panel - Entry and Results of Subject Information
        //VBox rightPanel = createRightPanel();
        //mainLayout.setRight(rightPanel);

        Scene scene = new Scene(mainLayout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLeftPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));

        Label titleLabel = new Label("과목 목록");

        subjectListView = new ListView<>(subjects);
        subjectListView.setPrefWidth(250);
        subjectListView.setPrefHeight(300);
        /*subjectListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displaySubjectDetails(newValue)
        );*/

        Button addButton = new Button("과목 추가");
        //addButton.setOnAction(e -> addNewSubject());

        Button removeButton = new Button("과목 삭제");
        //removeButton.setOnAction(e -> removeSelectedSubject());

        HBox buttonBox = new HBox(10, addButton, removeButton);

        panel.getChildren().addAll(titleLabel, subjectListView, buttonBox);
        return panel;
    }
}
