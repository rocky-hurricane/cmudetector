package org.opencv.gui.javafx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class StudentPageController {

    private Stage studentStage = new Stage();

    public Stage getStudentStage() {
        return studentStage;
    }

    public void setStudentStage(Stage studentStage) {
        this.studentStage = studentStage;
    }

    @FXML
    ImageView photo;

    @FXML
    TextField studentID;

    @FXML
    Text saveStatus;

    @FXML
    TextField first;

    @FXML
    TextField last;

    @FXML
    DatePicker dob;

    @FXML
    ChoiceBox<String> gender;

    @FXML
    ChoiceBox<String> program;

    @FXML
    ChoiceBox<String> reason;

    @FXML
    Button clear;

    @FXML
    Button save;

    @FXML
    public void clearText(){
        studentID.setText("");
        last.setText("");
        first.setText("");
        dob.getEditor().clear();
        gender.setValue("Unknown");
        program.setValue("Unknown");
        reason.setValue("No specific reason");

    }

    @FXML
    public void saveText(){
        String id = studentID.getText();
        String first = this.first.getText();
        String last = this.last.getText();
        LocalDate dob = this.dob.getValue();
        String gender = this.gender.getValue();
        String program = this.program.getValue();
        String reason = this.reason.getValue();
//        Student student = new Student(id, first, last, dob, gender, program);
        insert(id, first, last, dob, gender, program);
        saveStatus.setText(first + " " + last + "'s information is saved.");

    }


    public void showStudentPage(){
     try{
         // Load the fxml file and create a new stage for the popup dialog.
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/StudentPage.fxml"));
         Pane page = loader.load();

         // Create the dialog Stage.

         studentStage.setTitle("New Student");
         Scene scene = new Scene(page);
         studentStage.setScene(scene);


         // Show the dialog and wait until the user closes it
         studentStage.show();
     } catch (IOException e) {
         e.printStackTrace();
     }
    }

    public void insert(String id, String first, String last, LocalDate dob, String gender, String program){
        try(Connection con = DriverManager.getConnection("jdbc:derby:ReceptionDB", "user1", "1234")
        ) {
            String query = "insert into student values(?,?,?,?,?,?)";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, id);
            pStmt.setString(2,first);
            pStmt.setString(3,last);
            pStmt.setDate(4, java.sql.Date.valueOf(dob));
            pStmt.setString(5,gender);
            pStmt.setString(6,program);
            pStmt.executeUpdate();
            String query2 = "insert into record values(?,?,?)";
            PreparedStatement pStmt2 = con.prepareStatement(query2);
            pStmt2.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            pStmt2.setTime(2, Time.valueOf(LocalTime.now()));
            pStmt2.setString(3,id);
            pStmt2.executeUpdate();
            System.out.println("Saved");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
         try {
             reason.setItems(FXCollections.observableArrayList(
                     "No specific reason", "stapler", "tuition fees", "complaints", "collect assignments", "meet prople", "others"));
             reason.setValue("No specific reason");
             gender.setItems(FXCollections.observableArrayList(
                     "Male", "Female", "Unknown"));
             gender.setValue("Unknown");
             program.setItems(FXCollections.observableArrayList(
                     "MISM", "MSIT", "MSPPM", "Unknown"));
             program.setValue("Unknown");
             File file = new File("DD headshot.jpg");
             String localUrl = file.toURI().toURL().toString();
             photo.setImage(new Image(localUrl));
             studentID.setOnAction((event) ->

                     System.out.println("123"));
//             studentID.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> System.out.println(newValue));
                saveStatus.setText("");
         }catch (MalformedURLException e) {
             e.printStackTrace();
         }
    }
}
