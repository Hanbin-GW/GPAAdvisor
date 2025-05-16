package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;

public class GpaForm {
    @FXML private TextField subject1Field;
    @FXML private TextField subject2Field;
    @FXML private TextField subject3Field;
    @FXML private Label resultLabel;

    private final Map<String, Double> gradeMap = new HashMap<>();
    public GpaForm() {
        gradeMap.put("A+", 4.3); gradeMap.put("A", 4.0); gradeMap.put("A-", 3.7);
        gradeMap.put("B+", 3.3); gradeMap.put("B", 3.0); gradeMap.put("B-", 2.7);
        gradeMap.put("C+", 2.3); gradeMap.put("C", 2.0); gradeMap.put("C-", 1.7);
        gradeMap.put("D", 1.0); gradeMap.put("F", 0.0);
    }

    @FXML
    private void calculateGpa() {
        try {
            double g1 = convert(subject1Field.getText());
            double g2 = convert(subject2Field.getText());
            double g3 = convert(subject3Field.getText());

            double average = (g1 + g2 + g3) / 3.0;
            resultLabel.setText(String.format("✅ GPA: %.2f", average));
        } catch (Exception e) {
            resultLabel.setText("❌ 입력 오류! 성적을 A, B+, C 형식으로 입력하세요.");
        }
    }
    private double convert(String grade) {
        Double value = gradeMap.get(grade.toUpperCase().trim());
        if (value == null) throw new IllegalArgumentException("Invalid grade");
        return value;
    }
}
