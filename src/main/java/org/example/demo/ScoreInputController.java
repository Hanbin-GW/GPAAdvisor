package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreInputController {
    @FXML private VBox rootBox;

    private final List<TextField> scoreFields = new ArrayList<>();

    @FXML
    public void initialize() {
        List<GpaForm.Subject> subjects = GpaForm.subjectList;

        for (GpaForm.Subject s : subjects) {
            Label label = new Label(s.name + " 점수 입력:");
            TextField field = new TextField();
            field.setPromptText("예: 95");
            scoreFields.add(field);
            rootBox.getChildren().addAll(label, field);
        }
    }
    @FXML
    private void handleResult(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("result.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Result");
    }
    @FXML
    private void saveScores() {
        List<GpaForm.Subject> subjects = GpaForm.subjectList;

        for (int i = 0; i < subjects.size(); i++) {
            try {
                double score = Double.parseDouble(scoreFields.get(i).getText());
                subjects.get(i).score = score;
            } catch (Exception e) {
                subjects.get(i).score = Double.parseDouble(null);
            }
        }

        System.out.println("점수 저장 완료!");
        for (GpaForm.Subject s : subjects) {
            System.out.println(s.name + " → 점수: " + s.score);
        }
    }
}
