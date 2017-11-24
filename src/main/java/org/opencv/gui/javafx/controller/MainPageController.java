package org.opencv.gui.javafx.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.opencv.core.Mat;
import org.opencv.demo.core.DetectorsManager;
import org.opencv.demo.misc.Constants;
import org.opencv.demo.misc.FxLogger;
import org.opencv.demo.misc.ImageUtils;
import org.opencv.demo.model.Student;
import org.opencv.demo.service.impl.StudentServiceImpl;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
    @FXML
    ImageView video;
    @FXML
    Button recognize;

    private final VideoCapture camera = new VideoCapture();
    private Mat capturedImage = new Mat();
    private FxLogger fxLogger = new FxLogger();
    private ScheduledExecutorService timer;
    private String name = "";
    DetectorsManager detectorsManager= new DetectorsManager(fxLogger);
    private Map<Mat, String> detectResult = new HashMap<Mat, String>() ;
    public MainPageController() throws Exception {}

    @FXML
    public void initialize() throws Exception {

        //when initialize, recognizer will be assigned.

        //initialize certain parameters and status. like detector, isActive, etc.
        //-----------------initialize begin-----------------//
        boolean isActive = detectorsManager.changeRecognizerStatus();
        detectorsManager.clear();
        detectorsManager.addDetector(Constants.DEFAULT_FACE_CLASSIFIER);
        //-----------------initialize   end-----------------//

        //open camera and set initialization parameters
        camera.open(0) ;
        camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 560);
        camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 420);


        //grab the picture every 33 ms (30 frames/sec)
        Runnable frameGrabber = new Runnable() {
            @Override
            public void run(){
                camera.read(capturedImage);
                if (!capturedImage.empty()){
//                    detectResult = detectorsManager.detect(capturedImage, name);
                    capturedImage = detectorsManager.detect(capturedImage);
                    MainPageController.this.video.setImage(ImageUtils.mat2Image(capturedImage));
                }
            }
        };
        //set the refresh interval
        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);


        //---------------------------zahir start---------------------------
//        reason.setItems(FXCollections.observableArrayList(
//                "no specific reason", "stapler", "tuition fees", "complaints", "collect assignments", "meet prople", "others"));
//        reason.setValue("no specific reason");
//        choice();
        photo.setImage(new Image("http://www.marutaro.tw/marutaro/home/home_bg_M_down_photo.png"));
//        showPersonDetails("6666");
//        reasonField.setVisible(false);
//        reasonText.setVisible(false);

        //---------------------------zahir end---------------------------
    }


    private void showPersonDetails(String studentID) throws SQLException {

        StudentServiceImpl studentDao = new StudentServiceImpl();
        Student student = studentDao.getEntity(name);
        if (student != null){
            first.setText(student.getFirstName());
            last.setText(student.getLastName());
            studentIdText.setText(student.getStudentId());
            dob.setText(student.getDateBirth().toString());
            gender.setText(student.getGender());
            enrollmentYear.setText(student.getDateEnrollment());
            program.setText(student.getProgram());
        }


 //-----------------------zahir start------------------------------//
//        String url = "jdbc:derby:StudentInfoDB";
//        String username = "root";
//        String password = "123456";
//        String query = "SELECT * FROM Student where studentid = ?";
//
//
//        try (Connection con = DriverManager.getConnection(url, username, password)
//        ) {
//            PreparedStatement stmt = con.prepareStatement(query);
//            stmt.setString(1,studentID);
//            ResultSet rs = stmt.executeQuery();
//            rs.next();
////            System.out.println(rs.getString("LastName"));
//            first.setText(rs.getString("FirstName"));
//            last.setText(rs.getString("LastName"));
//            studentIdText.setText(rs.getString("studentID"));
//            dob.setText(rs.getString("dob"));
//            gender.setText(rs.getString("gender"));
//            enrollmentYear.setText(rs.getString("enrollmentyear"));
//            program.setText(rs.getString("program"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        //-----------------------zahir end------------------------------//

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

    //------------------------rocky start------------------------------//
    @FXML
    public void recognizeButtonAction() throws SQLException {
//        for (Map.Entry entry :detectResult.entrySet()){
//            name = entry.getValue().toString();
//        }
        name = detectorsManager.getName();
        System.out.println("-------------->>>>>>>"+ name);
        showPersonDetails(name);


//        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
//        String filePath = file.getPath();
//        filePath = this.getClass().getResource("").getPath();
//        System.out.println("----------->filePath"+filePath);

//        URL dir_url = ClassLoader.getSystemResource(Constants.TRAINING_FACES_PATH);
//        System.out.println(dir_url.toString());
    }




    //------------------------rocky   end------------------------------//




}
