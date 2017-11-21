package org.opencv.gui.javafx.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.*;


public class MainPageController {
    @FXML
    ImageView photo;
    @FXML
    Text studentIdText;
    @FXML
    ChoiceBox<String> reason;
    @FXML
    Text first;
    @FXML
    Text last;
    @FXML
    Text dob;
    @FXML
    Text gender;
    @FXML
    Text enrollmentYear;
    @FXML
    Text program;
    @FXML
    Text lastVisit;
    @FXML
    Text totalVisit;
    @FXML
    TextField reasonField;
    @FXML
    Text reasonText;
    @FXML
    Button approveButton;
    @FXML
    Button changeStudentButton;
    @FXML
    Button newStudentButton;
    @FXML
    Button newGuestButton;


    private void showPersonDetails(String studentID) {
        String url = "jdbc:derby:StudentInfoDB";
        String username = "root";
        String password = "123456";
        String query = "SELECT * FROM Student where studentid = ?";


        try (Connection con = DriverManager.getConnection(url, username, password)
        ) {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,studentID);
            ResultSet rs = stmt.executeQuery();
            rs.next();
//            System.out.println(rs.getString("LastName"));
            first.setText(rs.getString("FirstName"));
            last.setText(rs.getString("LastName"));
            studentIdText.setText(rs.getString("studentID"));
            dob.setText(rs.getString("dob"));
            gender.setText(rs.getString("gender"));
            enrollmentYear.setText(rs.getString("enrollmentyear"));
            program.setText(rs.getString("program"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeStudentButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
//        new ChangeStudentController().showChangeStudentPage();
        new ChangeStudentController().showChangeStudentPage();
    }
    @FXML
    private void newStudentButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        new StudentPageController().showStudentPage();
    }
    @FXML
    private void setNewGuestButton(ActionEvent event) {
        System.out.println("You clicked me!");
        new GuestPageController().showGuestPage();
    }

    public void choice() {
        reason.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    if(newValue.equals("others")){
                        reasonText.setVisible(true);
                        reasonField.setVisible(true);}

                    else{
                        reasonText.setVisible(false);
                        reasonField.setVisible(false);}
                }
                );
    }



        @FXML
    public void initialize(){
        reason.setItems(FXCollections.observableArrayList(
                "no specific reason", "stapler", "tuition fees", "complaints", "collect assignments", "meet prople", "others"));
        reason.setValue("no specific reason");
        choice();

        photo.setImage(new Image("http://www.marutaro.tw/marutaro/home/home_bg_M_down_photo.png"));
        showPersonDetails("6666");
        reasonField.setVisible(false);
        reasonText.setVisible(false);
    }
}
