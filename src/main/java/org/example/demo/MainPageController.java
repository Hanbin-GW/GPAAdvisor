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

    private void saveSubjectDetails() {
        Subject selected = subjectListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Input Error", "Select the subject you want to save first.");
            return;
        }

        // 입력값 유효성 검사
        if (nameField.getText().trim().isEmpty()) {
            showWarning("Input Error", "Please enter the subject name.");
            nameField.requestFocus();
            return;
        }

        try {
            // 학점 수 확인
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

            // 점수 입력 (모두 100점 만점)
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

            // 최종 성적 계산
            selected.calculateFinalGrade();

            // 성적 정보 표시
            gradeLabel.setText(String.format("calculated grades: %.2f (%s, GPA: %.1f)",
                    selected.getFinalScore(), selected.getLetterGrade(), selected.getGpaValue()));

            // 리스트뷰 갱신
            subjectListView.refresh();

            // GPA 다시 계산
            calculateGPA();

            // 목표 GPA와 비교하여 피드백 제공
            if (predictionCheckBox.isSelected()) {
                updatePrediction();

                // 목표 GPA가 설정되어 있는지 확인
                if (!targetGPAField.getText().trim().isEmpty()) {
                    try {
                        double targetGPA = Double.parseDouble(targetGPAField.getText().trim().replace(',', '.'));

                        // 현재 설정된 과목의 GPA 값
                        double currentSubjectGPA = selected.getGpaValue();

                        // 목표 달성 위한 최소 GPA
                        double neededGPA = calculateNeededGPA(selected);

                        if (currentSubjectGPA >= neededGPA) {
                            targetFeedbackLabel.setTextFill(Color.GREEN);
                            targetFeedbackLabel.setText("You can achieve your goal GPA with your current grades!");
                        } else {
                            targetFeedbackLabel.setTextFill(Color.RED);
                            targetFeedbackLabel.setText("It is difficult to achieve the goal GPA with current grades.");
                        }
                    } catch (NumberFormatException e) {
                        // 목표 GPA가 숫자가 아닌 경우
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
            predictionLabel.setText("목표 달성을 위한 최소 점수: ");
            targetFeedbackLabel.setText("");
            return;
        }

        Subject selected = subjectListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            predictionLabel.setText("목표 달성을 위한 최소 점수: 과목을 선택해주세요.");
            targetFeedbackLabel.setText("");
            return;
        }

        // 입력된 목표 GPA가 있는지 확인
        String targetGPAText = targetGPAField.getText().trim();
        if (targetGPAText.isEmpty()) {
            predictionLabel.setText("목표 달성을 위한 최소 점수: 목표 GPA를 입력해주세요.");
            targetFeedbackLabel.setText("");
            return;
        }

        try {
            // 쉼표를 점으로 변경 (로케일 문제 해결)
            targetGPAText = targetGPAText.replace(',', '.');
            double targetGPA = Double.parseDouble(targetGPAText);

            if (targetGPA < 0 || targetGPA > 4.5) {
                predictionLabel.setText("목표 달성을 위한 최소 점수: 목표 GPA는 0.0~4.5 사이여야 합니다.");
                targetFeedbackLabel.setText("");
                return;
            }

            // 현재 GPA 계산 (선택된 과목 제외)
            double totalQualityPoints = 0;
            double totalCredits = 0;

            for (Subject subject : subjects) {
                if (subject != selected) {
                    subject.calculateFinalGrade();
                    totalQualityPoints += subject.getGpaValue() * subject.getCredits();
                    totalCredits += subject.getCredits();
                }
            }

            // 목표 GPA를 달성하기 위해 선택한 과목에서 필요한 성적 계산
            double neededQualityPoints = (targetGPA * (totalCredits + selected.getCredits())) - totalQualityPoints;
            double neededGPA = neededQualityPoints / selected.getCredits();

            // 달성 가능 여부 확인 (필요한 GPA가 4.5 이상인 경우)
            if (neededGPA > 4.5) {
                targetFeedbackLabel.setTextFill(Color.RED);
                targetFeedbackLabel.setText("목표 GPA 달성이 불가능합니다. 다른 과목에서 더 높은 성적이 필요합니다.");
                predictionLabel.setText("목표 달성을 위한 최소 점수: 달성 불가능");
                return;
            } else if (neededGPA < 0) {
                // 목표 GPA가 너무 낮아 이미 달성했거나 어떤 성적이든 달성 가능한 경우
                targetFeedbackLabel.setTextFill(Color.GREEN);
                targetFeedbackLabel.setText("이미 목표 GPA를 달성했거나 쉽게 달성 가능합니다.");
                predictionLabel.setText("목표 달성을 위한 최소 점수: 이미 달성했습니다");
                return;
            }

            // GPA를 점수로 역변환
            double neededScore = convertFromGPA(neededGPA);
            String neededGrade = getLetterGradeFromGPA(neededGPA);

            predictionLabel.setText(String.format("목표 달성을 위한 최소 점수: %.2f점 (%s)", neededScore, neededGrade));

            // 현재 설정된 과목의 점수와 비교
            if (selected.getFinalScore() >= neededScore) {
                targetFeedbackLabel.setTextFill(Color.GREEN);
                targetFeedbackLabel.setText("현재 성적으로 목표 GPA 달성이 가능합니다!");
            } else {
                targetFeedbackLabel.setTextFill(Color.RED);
                targetFeedbackLabel.setText("현재 성적으로는 목표 GPA 달성이 어렵습니다.");
            }

        } catch (NumberFormatException e) {
            predictionLabel.setText("목표 달성을 위한 최소 점수: 유효한 목표 GPA를 입력해주세요.");
            targetFeedbackLabel.setText("");
        } catch (Exception e) {
            predictionLabel.setText("목표 달성을 위한 최소 점수: 계산 중 오류가 발생했습니다.");
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
        else return Math.max(0, gpa * 70 / 2.0); // F 범위 내에서 비례 계산
    }
}
