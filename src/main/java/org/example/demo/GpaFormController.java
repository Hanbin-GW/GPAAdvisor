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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GpaFormController {
    @FXML
    private void StartGPACalc(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gpa-form.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Main Page");
    }

    @FXML private TextField subject1Field;
    @FXML private TextField subject2Field;
    @FXML private TextField subject3Field;
    @FXML private Label resultLabel;

    private final Map<String, Double> gradeMap = new HashMap<>();

    public GpaFormController() {
        gradeMap.put("A+", 4.3); gradeMap.put("A", 4.0); gradeMap.put("A-", 3.7);
        gradeMap.put("B+", 3.3); gradeMap.put("B", 3.0); gradeMap.put("B-", 2.7);
        gradeMap.put("C+", 2.3); gradeMap.put("C", 2.0); gradeMap.put("C-", 1.7);
        gradeMap.put("D", 1.0); gradeMap.put("F", 0.0);
    }

    @FXML
    private void calculateGpa() {
        String g1 = subject1Field.getText().toUpperCase().trim();
        String g2 = subject2Field.getText().toUpperCase().trim();
        String g3 = subject3Field.getText().toUpperCase().trim();

        Double v1 = gradeMap.get(g1);
        Double v2 = gradeMap.get(g2);
        Double v3 = gradeMap.get(g3);

        if (v1 == null || v2 == null || v3 == null) {
            resultLabel.setText("❌ Invalid grade input. Use A, B+, C etc.");
            return;
        }

        double avg = (v1 + v2 + v3) / 3.0;
        resultLabel.setText(String.format("✅ Your GPA is: %.2f", avg));
    }
}
