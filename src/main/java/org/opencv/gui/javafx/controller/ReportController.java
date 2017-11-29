package org.opencv.gui.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.demo.dao.tools.JDBCTools;
import org.opencv.demo.model.StudentRecord;
import org.opencv.demo.service.impl.StudentRecordServiceImpl;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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
    public void dispRecord() throws SQLException {
        StudentRecordServiceImpl studentRecordService = new StudentRecordServiceImpl();
        recordTableF.setVisible(false);
        recordTable.setVisible(true);
        //1.根据条件查询
        //2.使用查询到的数据创建record对象
        //3.将对象存入data列表
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String program = chooseProgram.getValue();
        String gender = chooseGender.getValue();

        Connection con = JDBCTools.getConnection();

        if (startDate == null){startDate = LocalDate.of(2010, 1,1);}
        if(endDate == null){endDate = LocalDate.of(2100,1,1);}


        String query = "SELECT record.student_id, first_name, last_name, gender, program, date_visit, time_visit, reason " +
                "From record, student WHERE student.student_id = record.student_id " +
                "AND record.date_visit BETWEEN ? and ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setDate(1, Date.valueOf(startDate));
            pStmt.setString(2, String.valueOf(endDate));

            if (!program.equals("All")){
                query += " and program = ?";
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,program);
                if (!gender.equals("All")){
                    query += " and gender = ?";
                    pStmt = con.prepareStatement(query);
                    pStmt.setDate(1, Date.valueOf(startDate));
                    pStmt.setString(2, String.valueOf(endDate));
                    pStmt.setString(3,program);
                    pStmt.setString(4,gender);
                }
            }
            else if (!gender.equals("All")){
                query += " and gender = ?";
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,gender);
            }
            ResultSet rs = pStmt.executeQuery();
            addToList(rs);




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
                id = rs.getString("student_id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                gender = rs.getString("gender");
                program = rs.getString("program");
                date = rs.getDate("date_visit");
                time = rs.getTime("time_visit");
                reason = rs.getString("reason");
                StudentRecord record = new StudentRecord(id,firstName,lastName,gender,program,date,time,reason);
                data.add(record);
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
        recordTable.setVisible(false);
        recordTableF.setVisible(false);
        barChart.setVisible(false);
    }

    //-------------------------------yucheng start-------------------------------//
    @FXML
    TableView recordTableF;

    @FXML
    TableColumn studentIDColF;

    @FXML
    TableColumn firstNameColF;

    @FXML
    TableColumn lastNameColF;

    @FXML
    TableColumn genderColF;

    @FXML
    TableColumn programColF;

    @FXML
    TableColumn frequencyColF;

    @FXML
    Button frequency;

    @FXML
    CategoryAxis ca;

    @FXML
    NumberAxis na;

    @FXML
    Button showBarChart;

    @FXML
    BarChart<String, Integer> barChart;



    @FXML
    public void dispFreq() throws SQLException {
        recordTable.setVisible(false);
        recordTableF.setVisible(true);
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String program = chooseProgram.getValue();
        String gender = chooseGender.getValue();
        if (startDate == null){startDate = LocalDate.of(2010, 1,1);}
        if(endDate == null){endDate = LocalDate.of(2100,1,1);}

        /**
         * select table1.student_id, first_name, last_name, gender, program, frequency
         * from (select record.student_id, count(record.student_id) as frequency
         * from record where date_visit between '2017-11-12' and '2017-12-12' group by student_id)
         * as table1 left join student ON table1.student_id = student.student_id
         */

            String query = "select table1.student_id, firstname, lastname, gender, program, frequency " +
                    "from (select record.student_id, count(record.student_id) as frequency from record where date_visit between ? and ? group by id)" +
                    "as table1 left join student on table1.student_id = student.student_id";

            Connection con = JDBCTools.getConnection();
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setDate(1, Date.valueOf(startDate));
            pStmt.setString(2, String.valueOf(endDate));
            if (!program.equals("All")){
                query += " and program = ?";
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,program);
                if (!gender.equals("All")){
                    query += " and gender = ?";
                    pStmt = con.prepareStatement(query);
                    pStmt.setDate(1, Date.valueOf(startDate));
                    pStmt.setString(2, String.valueOf(endDate));
                    pStmt.setString(3,program);
                    pStmt.setString(4,gender);
                }
            }
            else if (!gender.equals("All")){
                query += " and gender = ?";
                System.out.println(query);
                pStmt = con.prepareStatement(query);
                pStmt.setDate(1, Date.valueOf(startDate));
                pStmt.setString(2, String.valueOf(endDate));
                pStmt.setString(3,gender);
            }
            ResultSet rs = pStmt.executeQuery();
            data.clear();
            while (rs.next()) {
                String id = rs.getString("student_id");
                int frequency = rs.getInt("frequency");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String genderSelected = rs.getString("gender");
                String programSelected = rs.getString("program");

                StudentRecord record = new StudentRecord(id,frequency,firstName,lastName,genderSelected,programSelected);
                data.add(record);

            }



        studentIDColF.setCellValueFactory(
                new PropertyValueFactory<>("studentID"));

        firstNameColF.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        lastNameColF.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        genderColF.setCellValueFactory(
                new PropertyValueFactory<>("gender"));

        programColF.setCellValueFactory(
                new PropertyValueFactory<>("program"));

        frequencyColF.setCellValueFactory(
                new PropertyValueFactory<>("frequency"));



        recordTableF.setItems(data);
    }

    @FXML
    public void showChart() throws SQLException {

        recordTable.setVisible(false);
        recordTableF.setVisible(false);
        barChart.setVisible(true);
        barChart.getData().clear();

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

            Connection con = JDBCTools.getConnection();

            if (startDate == null){startDate = LocalDate.of(2010, 1,1);}
            if(endDate == null){endDate = LocalDate.of(2100,1,1);}
            String query = "select program, count(program) from record left join student on record.student_id = student.student_id  where gender = 'Male' and date_visit between ? and ? group by program";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setDate(1, Date.valueOf(startDate));
            pStmt.setString(2, String.valueOf(endDate));
            ResultSet rs = pStmt.executeQuery();
            int [] male = {0,0,0};
            int j = 0;
            while (rs.next()){
                male[j] = rs.getInt(2);
                j ++;
            }
            query = "select program, count(program) from record left join student on record.student_id = student.student_id  where gender = 'Female' and date_visit between ? and ? group by program";
            pStmt = con.prepareStatement(query);
            pStmt.setDate(1, Date.valueOf(startDate));
            pStmt.setString(2, String.valueOf(endDate));
            rs = pStmt.executeQuery();
            int [] female = {0,0,0};
            int i = 0;
            while (rs.next()){
                female[i] = rs.getInt(2);
                i ++;
            }
            XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
            XYChart.Series<String, Integer> series2 = new XYChart.Series<>();
            ca.setCategories(FXCollections.observableArrayList("MSIT", "MISM", "MSPPM"));
            series1.getData().add(new XYChart.Data("MSIT", male[0]));
            series1.getData().add(new XYChart.Data("MISM", male[1]));
            series1.getData().add(new XYChart.Data("MSPPM", male[2]));
            series2.getData().add(new XYChart.Data("MSIT", female[0]));
            series2.getData().add(new XYChart.Data("MISM", female[1]));
            series2.getData().add(new XYChart.Data("MSPPM", female[2]));
            barChart.getData().add(series1);
            barChart.getData().add(series2);
            ca.setLabel("Programs");
            na.setLabel("Frequency");
            series1.setName("Male");
            series2.setName("Female");



    }


//--------------------------yucheng ends-------------------------------//

}
