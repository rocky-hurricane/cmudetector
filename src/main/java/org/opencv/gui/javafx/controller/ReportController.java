package org.opencv.gui.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportController {

    ObservableList<Record> data =
            FXCollections.observableArrayList();


    @FXML
    DatePicker startDatePicker;

    @FXML
    DatePicker endDatePicker;

    @FXML
    ChoiceBox<String> chooseProgram;

    @FXML
    ChoiceBox<String> chooseGender;

//    @FXML
//    ChoiceBox<String> chooseReason;

    @FXML
    TableView recordTable;

    @FXML
    TableColumn studentIDCol;

    @FXML
    TableColumn firstNameCol;

    @FXML
    TableColumn lastNameCol;

    @FXML
    TableColumn genderCol;

    @FXML
    TableColumn programCol;

    @FXML
    TableColumn reasonCol;

    @FXML
    TableColumn dateCol;

    @FXML
    TableColumn timeCol;

    @FXML
    Label countStudent;

    @FXML
    Label countTime;

    @FXML
    Button search;

    public void showReport(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("Visit Report");

            Scene scene = new Scene(page);
            stage.setScene(scene);



            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dispResult(){
        //1.根据条件查询
        //2.使用查询到的数据创建record对象
        //3.将对象存入data列表

        studentIDCol.setCellValueFactory(
                new PropertyValueFactory<>("studentID"));

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        genderCol.setCellValueFactory(
                new PropertyValueFactory<>("gender"));

        programCol.setCellValueFactory(
                new PropertyValueFactory<>("program"));

        reasonCol.setCellValueFactory(
                new PropertyValueFactory<>("reason"));

        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        timeCol.setCellValueFactory(
                new PropertyValueFactory<>("time"));

        recordTable.setItems(data);
    }


    public void initialize() {

    }



}
