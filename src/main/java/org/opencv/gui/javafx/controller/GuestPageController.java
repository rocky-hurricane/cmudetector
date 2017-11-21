package org.opencv.gui.javafx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class GuestPageController {

    @FXML
    ImageView photo;

    @FXML
    TextField guestID;

    @FXML
    TextField first;

    @FXML
    TextField last;

    @FXML
    ChoiceBox<String> gender;

    @FXML
    ChoiceBox<String> reason;

    @FXML
    Button clear;

    @FXML
    Button save;

    @FXML
    public void clearText(){
        guestID.setText("");
        last.setText("");
        first.setText("");
        gender.setValue("Unknown");
        reason.setValue("No specific reason");
    }

    @FXML
    public void saveText(){
        String id = guestID.getText();
        String first = this.first.getText();
        String last = this.last.getText();
        String gender = this.gender.getValue();
        String reason = this.reason.getValue();
        Guest guest = new Guest(id, first, last,  gender, reason);
        System.out.println(guest);
    }

    public void showGuestPage(){
        try{// Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/GuestPage.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("New Guest");
            Scene scene = new Scene(page);
            stage.setScene(scene);


            // Show the dialog and wait until the user closes it
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        reason.setItems(FXCollections.observableArrayList(
                "No specific reason", "stapler", "tuition fees", "complaints", "collect assignments", "meet prople", "others"));
        reason.setValue("No specific reason");
        gender.setItems(FXCollections.observableArrayList("Male", "Female", "Unknown"));
        gender.setValue("Unknown");
        File file = new File("DD headshot.jpg");
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        photo.setImage(new Image(localUrl));
    }
}
