package org.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

public class MainPageController extends Application {

    private static class Subject {
        private String name;
        private double homework, quiz, test, exam;
        private double homeworkWeight = 30, quizWeight = 20, testWeight = 30, examWeight = 20; // 사용자 지정 비율
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

        // Left Panel - Subject List Left Panel - Subject List
        VBox leftPanel = createLeftPanel();
        mainLayout.setLeft(leftPanel);

        // Right Panel - Entry and Results of Subject Information
        VBox rightPanel = createRightPanel();
        mainLayout.setRight(rightPanel);

        Scene scene = new Scene(mainLayout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createLeftPanel() {
        TextField hwWeightField = new TextField("30");
        TextField qzWeightField = new TextField("20");
        TextField tsWeightField = new TextField("30");
        TextField exWeightField = new TextField("20");

        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));

        Label titleLabel = new Label("과목 목록");

        subjectListView = new ListView<>(subjects);
        subjectListView.setPrefWidth(250);
        subjectListView.setPrefHeight(300);
        subjectListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> displaySubjectDetails(newValue)
        );

        Button addButton = new Button("과목 추가");
        addButton.setOnAction(e -> addNewSubject());

        Button removeButton = new Button("과목 삭제");
        removeButton.setOnAction(e -> removeSelectedSubject());

        HBox buttonBox = new HBox(10, addButton, removeButton);

        panel.getChildren().addAll(titleLabel, subjectListView, buttonBox);
        return panel;
    }

    private VBox createRightPanel() {
        TextField hwWeightField = new TextField("30");
        TextField qzWeightField = new TextField("20");
        TextField tsWeightField = new TextField("30");
        TextField exWeightField = new TextField("20");
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("Subject name:"), 0, 0);
        nameField = new TextField();
        inputGrid.add(nameField, 1, 0);

        inputGrid.add(new Label("Number of credits:"), 0, 1);
        creditsField = new TextField();
        creditsField.setPromptText("3.0");
        inputGrid.add(creditsField, 1, 1);

        inputGrid.add(new Label("Homework (Test: 30%):"), 0, 2);
        homeworkField = new TextField();
        inputGrid.add(homeworkField, 1, 2);
        inputGrid.add(new Label("homework percentage(%):"), 2, 2);
        inputGrid.add(hwWeightField, 3, 2);

        inputGrid.add(new Label("Quiz (Test: 20%):"), 0, 3);
        quizField = new TextField();
        inputGrid.add(quizField, 1, 3);
        inputGrid.add(new Label("Quiz percentage(%):"), 2, 3);
        inputGrid.add(qzWeightField, 3, 3);

        inputGrid.add(new Label("Test (Test: 30%):"), 0, 4);
        inputGrid.add(testField, 1, 4);
        inputGrid.add(new Label("Test percentage(%):"), 2, 4);
        inputGrid.add(tsWeightField, 3, 4);

        inputGrid.add(new Label("Exam (Test: 20%):"), 0, 5);
        examField = new TextField();
        inputGrid.add(examField, 1, 5);
        inputGrid.add(new Label("시험 비율(%):"), 2, 5);
        inputGrid.add(exWeightField, 3, 5);

        gradeLabel = new Label("Calculated credit: ");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveSubjectDetails());

        // GPA Calculation Area
        HBox gpaBox = new HBox(10);
        Button calculateButton = new Button("GPA Calculate");
        calculateButton.setOnAction(e -> calculateGPA());
        resultLabel = new Label("GPA: ");
        gpaBox.getChildren().addAll(calculateButton, resultLabel);

        gpaBox.setPadding(new Insets(10));

        // Expectation Area
        VBox predictionBox = new VBox(10);
        HBox targetBox = new HBox(10);

        Label targetGPALabel = new Label("Target GPA:");
        targetGPAField = new TextField();
        targetGPAField.setPromptText("4.0");
        targetGPAField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update forecast when target GPA changes
            if (predictionCheckBox.isSelected()) {
                 updatePrediction();
            }
        });
        targetBox.getChildren().addAll(targetGPALabel, targetGPAField);

        predictionCheckBox = new CheckBox("Enable Expectation");
        predictionCheckBox.setOnAction(e -> updatePrediction());

        predictionLabel = new Label("Minimum score to achieve the goal: ");
        targetFeedbackLabel = new Label("");
        targetFeedbackLabel.setTextFill(Color.BLUE);

        predictionBox.getChildren().addAll(targetBox, predictionCheckBox, predictionLabel, targetFeedbackLabel);

        panel.getChildren().addAll(
                new Label("Subject Information"), inputGrid, gradeLabel, saveButton,
                new Separator(), gpaBox,
                new Separator(), predictionBox
        );

        return panel;
    }

	public void addNewSubject(){
		TextInputDialog dialog = new TextInputDialog("new subject");
		dialog.setTitle("Add a subject");
        dialog.setHeaderText("insert a new subject's name");
        dialog.setContentText("subject name:");

        Optional<String> result = dialog.showAndWait();

		if (result.isPresent() && !result.get().trim().isEmpty()) {
            Subject newSubject = new Subject(result.get().trim(), 3.0);
            subjects.add(newSubject);
            subjectListView.getSelectionModel().select(newSubject);

            // Reset the field
            clearFields();
            nameField.setText(newSubject.getName());
            creditsField.setText("3.0");

            showInformation("Add a subject", newSubject.getName() + "the subject was add.");
        }
	}
    private void addNewSubject() {
        // Display Name Input Dialog
        TextInputDialog dialog = new TextInputDialog("새 과목");
        dialog.setTitle("과목 추가");
        dialog.setHeaderText("새 과목의 이름을 입력하세요");
        dialog.setContentText("과목명:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().trim().isEmpty()) {
            Subject newSubject = new Subject(result.get().trim(), 3.0);
            subjects.add(newSubject);
            subjectListView.getSelectionModel().select(newSubject);

            // Reset Field
            clearFields();
            nameField.setText(newSubject.getName());
            creditsField.setText("3.0");

            showInformation("과목 추가", newSubject.getName() + " 과목이 추가되었습니다.");
        }
    }

}
