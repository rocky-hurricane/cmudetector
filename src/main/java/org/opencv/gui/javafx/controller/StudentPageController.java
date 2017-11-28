package org.opencv.gui.javafx.controller;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.demo.core.DetectorsManager;
import org.opencv.demo.core.ElementsDetector;
import org.opencv.demo.misc.Constants;
import org.opencv.demo.misc.FxLogger;
import org.opencv.demo.misc.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class StudentPageController {

    private Stage studentStage = new Stage();

    public StudentPageController() throws Exception {
    }

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
    TextField enrollment;

    @FXML
    Button clear;

    @FXML
    Button save;

    FxLogger fxLogger = new FxLogger();
//    DetectorsManager detectorsManager;
    private List<BufferedImage> capturedFaces = new ArrayList();
    private BufferedImage image;
    private ScheduledExecutorService timer;
    MainPageController mainPageController;

    @FXML
    public void captureButtonAction(){

            Runnable framCapture = new Runnable(){
                boolean isShutdown = false;
                @Override
                public void run() {
                    while (!isShutdown) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ElementsDetector detector = mainPageController.detectorsManager.getDetector(Constants.DEFAULT_FACE_CLASSIFIER);
                        System.out.println(capturedFaces.size()+"--------->");
                        System.out.println(detector);
//                    System.out.println("------>detector"+detector.getDetectedElements());
                        // if no face detected from webcam, just skips
                        if (detector.getDetectedElements().size() != 1) {
                            continue;
                        }
                        System.out.println(capturedFaces.size()+">>>>>>>>>>>>>");
                        Mat face = ImageUtils.resizeFace(detector.getDetectedElements().get(0).getDetectedImageElement());
                        image = ImageUtils.getBufferedImageFromMat(face);

                        capturedFaces.add(image);
                        photo.setImage((SwingFXUtils.toFXImage(image,null)));
                        if (capturedFaces.size()>Constants.MINIMUM_TRAIN_SET_SIZE){
                            return;
                        }

                    }
                }
            };
//            this.timer = Executors.newSingleThreadScheduledExecutor();
//            this.timer.scheduleAtFixedRate(framCapture, 0, 100, TimeUnit.MILLISECONDS);

        Thread t = new Thread(framCapture);
        t.start();
        }



    @FXML
    public void clearText(){
        studentID.setText("");
        last.setText("");
        first.setText("");
        dob.getEditor().clear();
        gender.setValue("Unknown");
        program.setValue("Unknown");

    }

    @FXML
    public void saveText() throws Exception {
        String id = studentID.getText();
        String first = this.first.getText();
        String last = this.last.getText();
        LocalDate dob = this.dob.getValue();
        String gender = this.gender.getValue();
        String program = this.program.getValue();
//        Student student = new Student(id, first, last, dob, gender, program);
//        insert(id, first, last, dob, gender, program);
        saveStatus.setText(first + " " + last + "'s information is saved.");
        int counter=0;
        for (BufferedImage capturedFace : capturedFaces){
            ImageIO.write(capturedFace, "JPG", new File(Constants.FACES_FILE_PATH + File.separatorChar + this.studentID.getText() + "_" + (counter++) + ".jpg"));
        }
        mainPageController.detectorsManager = new DetectorsManager(mainPageController.fxLogger);
        boolean isActive = mainPageController.detectorsManager.changeRecognizerStatus();
        mainPageController.detectorsManager.clear();
        mainPageController.detectorsManager.addDetector(Constants.DEFAULT_FACE_CLASSIFIER);
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
             gender.setItems(FXCollections.observableArrayList(
                     "Male", "Female", "Unknown"));
             gender.setValue("Unknown");
             program.setItems(FXCollections.observableArrayList(
                     "MISM", "MSIT", "MSPPM", "Unknown"));
             program.setValue("Unknown");
             File file = new File("DD headshot.jpg");
             String localUrl = file.toURI().toURL().toString();
             photo.setImage(new Image("http://www.marutaro.tw/marutaro/home/home_bg_M_down_photo.png"));

                saveStatus.setText("");
         }catch (MalformedURLException e) {
             e.printStackTrace();
         }
    }
}
