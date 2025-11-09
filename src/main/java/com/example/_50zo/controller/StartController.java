package com.example._50zo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;

public class StartController {

    @FXML
    private Button startButton;

    @FXML
    void HelpBtnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información importante");
        alert.setHeaderText("Acción completada con éxito");
        alert.setResizable(true);
        DialogPane dialogPane = alert.getDialogPane();
        TextArea textArea = new TextArea("asd" +
                "asd" +
                "asd" +
                "as" +
                "d");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(300, 300);
        dialogPane.setContent(textArea);
        dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("information-label");
        alert.getDialogPane().setPrefSize(550, 50);
        alert.showAndWait();

    }

}


