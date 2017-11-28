package org.opencv.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class MainApp extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Parent root = FXMLLoader.load(getClass().getResource("/fx/MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }



}

