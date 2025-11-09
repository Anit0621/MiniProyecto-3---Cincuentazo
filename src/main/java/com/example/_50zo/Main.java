package com.example._50zo;

import com.example._50zo.view.StartStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage primaryStage) throws IOException {
        StartStage.getInstance();

  // Clase Java FX que arranca la app y que cargue la primer vista del inicio
    }
}


//Nota: en pruebas unitarias podemos usar Decktest, playertest y gametest y poner unos dos o tres metodos a cada una c:
