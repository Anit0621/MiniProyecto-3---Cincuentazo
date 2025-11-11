package com.example._50zo;

import com.example._50zo.view.StartStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        StartStage.getInstance();

    }
}


