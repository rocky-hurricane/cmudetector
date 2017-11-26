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
import org.opencv.demo.model.StudentRecord;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ReportController {

    ObservableList<StudentRecord> data =
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
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String program = chooseProgram.getValue();
        String gender = chooseGender.getValue();
        try(Connection con = DriverManager.getConnection("jdbc:derby:ReceptionDB", "user1", "1234")
        ) {
            String query = "select id, firstname, lastname, gender, program, dateofvisit, timeofvisit, reason " +
                    "from (record left join student on record.id = student.studentid) " +
                    "where dateofvisit between ? and ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setDate(1, Date.valueOf(startDate));
            pStmt.setString(2, String.valueOf(endDate));
            if (!program.equals("All")){
                query += "and program = ?";
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,program);
                if (!gender.equals("All")){
                    query += "and gender = ?";
                    pStmt = con.prepareStatement(query);
                    pStmt.setDate(1, Date.valueOf(startDate));
                    pStmt.setString(2, String.valueOf(endDate));
                    pStmt.setString(3,program);
                    pStmt.setString(4,gender);
                }
            }
            else if (!gender.equals("All")){
                query += "and gender = ?";
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,gender);
            }
            ResultSet rs = pStmt.executeQuery();
            addToList(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        studentIDCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

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

    public void addToList(ResultSet rs){
        String id;
        String firstName;
        String lastName;
        String gender;
        String program;
        Date date;
        Time time;
        String reason;
        try {
            data.clear();
            while (rs.next()) {
                id = rs.getString("id");
                firstName = rs.getString("FirstName");
                lastName = rs.getString("LastName");
                gender = rs.getString("gender");
                program = rs.getString("program");
                date = rs.getDate("dateofvisit");
                time = rs.getTime("timeofvisit");
                reason = "test";
                StudentRecord studentRecord = new StudentRecord(id,firstName,lastName,gender,program,date,time,reason);
                data.add(studentRecord);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        chooseGender.setItems(FXCollections.observableArrayList(
                "Male", "Female", "All"));
        chooseGender.setValue("All");
        chooseProgram.setItems(FXCollections.observableArrayList(
                "MISM", "MSIT", "MSPPM", "All"));
        chooseProgram.setValue("All");
    }



}
