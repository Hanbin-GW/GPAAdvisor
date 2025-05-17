package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class Result {
    @FXML private Label result;

    @FXML
    private void calculateGpa(){
        List<GpaForm.Subject> subjects = GpaForm.subjectList;
        double total = 0.0;
        double average = 0.0;
        for (int i = 0; i < subjects.size(); i++) {
            double score = subjects.get(i).score;

            total += score;
        }

        average = total / subjects.size();

        result.setText(String.format("%.2f", average));
    }
}
