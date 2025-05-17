package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GpaForm {
    @FXML private TextField subjectNameField;
    @FXML private TextField creditField;
    @FXML private TextField gradeField;
    @FXML private Label statusLabel;
    @FXML private Label resultLabel;

    public static class Subject {
        String name;
        double credit;
        double score;

        Subject(String name, double credit, double score) {
            this.name = name;
            this.credit = credit;
            this.score = score;
        }
    }

    public static List<Subject> subjectList = new ArrayList<>();
    private final int MAX_SUBJECTS = 5;

    @FXML
    private void addSubject() {
        if (subjectList.size() >= MAX_SUBJECTS) {
            statusLabel.setText("❌ 이미 5개 등록됨");
            return;
        }

        try {
            String name = subjectNameField.getText().trim();
            double credit = Double.parseDouble(creditField.getText().trim());
            double gpa = Double.parseDouble(gradeField.getText().trim());

            subjectList.add(new Subject(name, credit, gpa));
            int count = subjectList.size();
            statusLabel.setText("✅ 등록됨: " + name + " (" + count + "/5)");
            resultLabel.setText("");

            // 입력 초기화
            subjectNameField.clear();
            creditField.clear();
            gradeField.clear();

        } catch (Exception e) {
            resultLabel.setText("❌ 입력 오류 (숫자 확인)");
        }
    }

    @FXML
    private void calculateTotalGpa() {
        if (subjectList.size() < MAX_SUBJECTS) {
            resultLabel.setText("❗ 5개 과목을 모두 등록해주세요");
            return;
        }

        double totalCredits = 0;
        double totalPoints = 0;

        for (Subject s : subjectList) {
            totalCredits += s.credit;
            totalPoints += s.credit * s.score;
        }

        double avg = totalPoints / totalCredits;
        resultLabel.setText(String.format("🎓 총 GPA: %.2f", avg));
    }
    @FXML
    private void goToScorePage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("score-input.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("점수 입력");
    }

}
