package org.opencv.gui.javafx.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class ChangeStudentController {

    @FXML
    TextField studentID;

    @FXML
    Button clear;

    @FXML
    Button save;

    @FXML
    public void clearText(){
        studentID.setText("");
    }

    @FXML
    public void saveText(){
//        mainapp.showStudentEditDialog();
        Stage temp = (Stage) save.getScene().getWindow();
        temp.close();
    }

    public void showChangeStudentPage(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/Change_student.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage stage = new Stage();

            Scene scene = new Scene(page);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
