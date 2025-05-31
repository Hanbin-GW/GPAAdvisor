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
                 //updatePrediction();
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
            //clearFields();
            nameField.setText(newSubject.getName());
            creditsField.setText("3.0");

            showInformation("Add a subject", newSubject.getName() + "the subject was add.");
        }
	}

    private void removeSelectedSubject() {
        Subject selected = subjectListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Confirm deletion dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Subject");
            alert.setHeaderText("Checking to delete the subject");
            alert.setContentText("Do you really remove '" + selected.getName() + "' Subject?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                subjects.remove(selected);
                clearFields();

                // Recalculate GPA
                calculateGPA();

                // Update Prediction
                if (predictionCheckBox.isSelected()) {
                    updatePrediction();
                }
            }
        } else {
            showWarning("Remove Subject", "Please select the subject you want to delete.");
        }
    }

    private void displaySubjectDetails(Subject subject) {
        if (subject != null) {
            nameField.setText(subject.getName());
            creditsField.setText(String.valueOf(subject.getCredits()));
            homeworkField.setText(String.valueOf(subject.getHomework()));
            quizField.setText(String.valueOf(subject.getQuiz()));
            testField.setText(String.valueOf(subject.getTest()));
            examField.setText(String.valueOf(subject.getExam()));

            // Displaying grade information
            if (subject.getFinalScore() > 0) {
                gradeLabel.setText(String.format("Calculated grades: %.2f (%s, GPA: %.1f)",
                        subject.getFinalScore(), subject.getLetterGrade(), subject.getGpaValue()));
            } else {
                gradeLabel.setText("Calculated grades: Not yet");
            }

            // Update if prediction is enabled
            if (predictionCheckBox.isSelected()) {
                updatePrediction();
            }
        }
    }

    private void saveSubjectDetails() {
        Subject selected = subjectListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Input Error", "Select the subject you want to save first.");
            return;
        }

        // Input Validation
        if (nameField.getText().trim().isEmpty()) {
            showWarning("Input Error", "Please enter the subject name.");
            nameField.requestFocus();
            return;
        }

        try {
            // Check the number of credits
            double credits;
            try {
                credits = Double.parseDouble(creditsField.getText().trim().replace(',', '.'));
                if (credits <= 0) {
                    showWarning("Input Error", "The number of credits must be greater than zero.");
                    creditsField.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showWarning("Input Error", "Please enter the number of credits.");
                creditsField.requestFocus();
                return;
            }

            // Enter scores (all out of 100)
            double homework, quiz, test, exam;

            try {
                homework = Double.parseDouble(homeworkField.getText().trim().replace(',', '.'));
                if (homework < 0 || homework > 100) {
                    showWarning("Input error", "Please enter a value between 0 and 100 for your homework score.");
                    homeworkField.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showWarning("Input error", "Please enter the number only for the homework score.");
                homeworkField.requestFocus();
                return;
            }

            try {
                quiz = Double.parseDouble(quizField.getText().trim().replace(',', '.'));
                if (quiz < 0 || quiz > 100) {
                    showWarning("Input error", "Please enter a value between 0 and 100 for the quiz score.");
                    quizField.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showWarning("Input error", "Please enter numbers only for the quiz score.");
                quizField.requestFocus();
                return;
            }

            try {
                test = Double.parseDouble(testField.getText().trim().replace(',', '.'));
                if (test < 0 || test > 100) {
                    showWarning("Input error", "Please enter a value between 0 and 100 for the test score");
                    testField.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showWarning("Input error", "Please enter only numbers for the test score.");
                testField.requestFocus();
                return;
            }

            try {
                exam = Double.parseDouble(examField.getText().trim().replace(',', '.'));
                if (exam < 0 || exam > 100) {
                    showWarning("Input error", "Please enter a value between 0 and 100 for the exam score.");
                    examField.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                showWarning("Input error", "Please enter the number only for the exam score.");
                examField.requestFocus();
                return;
            }

            // 값이 모두 유효하면 과목에 저장
            selected.setName(nameField.getText().trim());
            selected.setCredits(credits);
            selected.setHomework(homework);
            selected.setQuiz(quiz);
            selected.setTest(test);
            selected.setExam(exam);

            // final grade calculation
            selected.calculateFinalGrade();

            // Displaying grade information
            gradeLabel.setText(String.format("calculated grades: %.2f (%s, GPA: %.1f)",
                    selected.getFinalScore(), selected.getLetterGrade(), selected.getGpaValue()));

            // Update List View
            subjectListView.refresh();

            // Recalculate GPA
            calculateGPA();

            // Provide feedback compared to target GPA
            if (predictionCheckBox.isSelected()) {
                updatePrediction();

                // Check that the target GPA is set
                if (!targetGPAField.getText().trim().isEmpty()) {
                    try {
                        double targetGPA = Double.parseDouble(targetGPAField.getText().trim().replace(',', '.'));

                        // GPA values for the currently set subject
                        double currentSubjectGPA = selected.getGpaValue();

                        // Minimum GPA to Achieve Target
                        double neededGPA = calculateNeededGPA(selected);

                        if (currentSubjectGPA >= neededGPA) {
                            targetFeedbackLabel.setTextFill(Color.GREEN);
                            targetFeedbackLabel.setText("You can achieve your goal GPA with your current grades!");
                        } else {
                            targetFeedbackLabel.setTextFill(Color.RED);
                            targetFeedbackLabel.setText("It is difficult to achieve the goal GPA with current grades.");
                        }
                    } catch (NumberFormatException e) {
                        // Target GPA is not a number
                        targetFeedbackLabel.setText("");
                    }
                }
            }

            showInformation("Save Score", "'" + selected.getName() + "' Subjects score was saved.");

        } catch (Exception e) {
            showError("Error Occurred", "The error Occurred during the process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        creditsField.clear();
        homeworkField.clear();
        quizField.clear();
        testField.clear();
        examField.clear();
        gradeLabel.setText("Calculated grades: Not yet");
        targetFeedbackLabel.setText("");
    }

    private void calculateGPA() {
        if (subjects.isEmpty()) {
            resultLabel.setText("GPA: Add a subject.");
            currentGPA = 0.0;
            return;
        }

        double totalQualityPoints = 0;
        double totalCredits = 0;

        for (Subject subject : subjects) {
            subject.calculateFinalGrade();
            totalQualityPoints += subject.getGpaValue() * subject.getCredits();
            totalCredits += subject.getCredits();
        }

        if (totalCredits > 0) {
            currentGPA = totalQualityPoints / totalCredits;
            resultLabel.setText(String.format("GPA: %.2f", currentGPA));
        } else {
            resultLabel.setText("GPA: Your credits are not entered.");
            currentGPA = 0.0;
        }
    }
    private double calculateNeededGPA(Subject selectedSubject) {
        double totalQualityPoints = 0;
        double totalCredits = 0;

        // Check a target GPA
        double targetGPA;
        try {
            targetGPA = Double.parseDouble(targetGPAField.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE; // Invalid target GPA
        }

        // Calculate the sum of quality scores and credits for all other subjects except selected subjects
        for (Subject subject : subjects) {
            if (subject != selectedSubject) {
                subject.calculateFinalGrade();
                totalQualityPoints += subject.getGpaValue() * subject.getCredits();
                totalCredits += subject.getCredits();
            }
        }

        // Calculate grades required in the selected subject to achieve the target GPA
        double neededQualityPoints = (targetGPA * (totalCredits + selectedSubject.getCredits())) - totalQualityPoints;
        return neededQualityPoints / selectedSubject.getCredits();
    }

    private void updatePrediction() {
        if (!predictionCheckBox.isSelected()) {
            predictionLabel.setText("Minimum score to achieve the goal: ");
            targetFeedbackLabel.setText("");
            return;
        }

        Subject selected = subjectListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            predictionLabel.setText("Minimum Score to Achieve Your Goal: Please Choose a Subject.");
            targetFeedbackLabel.setText("");
            return;
        }

        // Verify that the entered target GPA exists
        String targetGPAText = targetGPAField.getText().trim();
        if (targetGPAText.isEmpty()) {
            predictionLabel.setText("Minimum score to achieve goal: Please enter your goal GPA.");
            targetFeedbackLabel.setText("");
            return;
        }

        try {
            // Change commas to dots (resolve locale issues)
            targetGPAText = targetGPAText.replace(',', '.');
            double targetGPA = Double.parseDouble(targetGPAText);

            if (targetGPA < 0 || targetGPA > 4.5) {
                predictionLabel.setText("목표 달성을 위한 최소 점수: 목표 GPA는 0.0~4.5 사이여야 합니다.");
                targetFeedbackLabel.setText("");
                return;
            }

            // Current GPA calculation (excluding selected subjects)
            double totalQualityPoints = 0;
            double totalCredits = 0;

            for (Subject subject : subjects) {
                if (subject != selected) {
                    subject.calculateFinalGrade();
                    totalQualityPoints += subject.getGpaValue() * subject.getCredits();
                    totalCredits += subject.getCredits();
                }
            }

            // Calculate grades required in the selected subject to achieve the target GPA
            double neededQualityPoints = (targetGPA * (totalCredits + selected.getCredits())) - totalQualityPoints;
            double neededGPA = neededQualityPoints / selected.getCredits();

            // Confirmation of attainability (if required GPA is greater than 4.5)
            if (neededGPA > 4.5) {
                targetFeedbackLabel.setTextFill(Color.RED);
                targetFeedbackLabel.setText("Target GPA Not achievable. Need higher grades in other subjects.");
                predictionLabel.setText("Minimum score to achieve goal: Not achievable");
                return;
            } else if (neededGPA < 0) {
                // If the target GPA is too low to be achieved or any grade is achievable
                targetFeedbackLabel.setTextFill(Color.GREEN);
                targetFeedbackLabel.setText("You have already achieved your target GPA or are easily achievable.");
                predictionLabel.setText("Minimum score to achieve goal: already achieved");
                return;
            }

            // Reverse GPA to Score
            double neededScore = convertFromGPA(neededGPA);
            String neededGrade = getLetterGradeFromGPA(neededGPA);

            predictionLabel.setText(String.format("Minimum score to achieve the goal: %.2f (%s)", neededScore, neededGrade));

            // Compare to the score of the currently set subject
            if (selected.getFinalScore() >= neededScore) {
                targetFeedbackLabel.setTextFill(Color.GREEN);
                targetFeedbackLabel.setText("You can achieve your goal GPA with your current grades!");
            } else {
                targetFeedbackLabel.setTextFill(Color.RED);
                targetFeedbackLabel.setText("It is difficult to achieve the goal GPA with current grades.");
            }

        } catch (NumberFormatException e) {
            predictionLabel.setText("Minimum Score to Achieve Goal: Please enter a valid Goal GPA.");
            targetFeedbackLabel.setText("");
        } catch (Exception e) {
            predictionLabel.setText("Minimum score to achieve goal: Error during calculation.");
            targetFeedbackLabel.setText("");
            e.printStackTrace();
        }
    }
    private double convertFromGPA(double gpa) {
        // Reverse GPA to 100 points
        if (gpa >= 4.5) return 97;
        else if (gpa >= 4.3) return 93;
        else if (gpa >= 4.0) return 90;
        else if (gpa >= 3.5) return 87;
        else if (gpa >= 3.3) return 83;
        else if (gpa >= 3.0) return 80;
        else if (gpa >= 2.5) return 77;
        else if (gpa >= 2.3) return 73;
        else if (gpa >= 2.0) return 70;
        else return Math.max(0, gpa * 70 / 2.0); // Proportional calculation within F range
    }
    private String getLetterGradeFromGPA(double gpa) {
        // Converting GPA to Credit
        if (gpa >= 4.5) return "A+";
        else if (gpa >= 4.3) return "A0";
        else if (gpa >= 4.0) return "A-";
        else if (gpa >= 3.5) return "B+";
        else if (gpa >= 3.3) return "B0";
        else if (gpa >= 3.0) return "B-";
        else if (gpa >= 2.5) return "C+";
        else if (gpa >= 2.3) return "C0";
        else if (gpa >= 2.0) return "C-";
        else return "F";
    }
    // Notification window display methods
    private void showInformation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
