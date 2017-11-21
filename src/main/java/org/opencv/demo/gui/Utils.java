package org.opencv.demo.gui;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }



    /**
     * @param dataDirectoryName       * @param dataDirectoryName
     * @return return all files in the haarcascades directory.
     * @throws URISyntaxException
     */
    public static Map<String, String[]> loadClassifiers(String dataDirectoryName) throws URISyntaxException {
        Map<String, String[]> classifiers = new HashMap<>();
        URL dir_url = ClassLoader.getSystemResource(dataDirectoryName);
        // file:/Users/rocky/CMU/SEMESTER1/JAVA/ASSIGNMENT/groupProject/cmu_detector/target/classes/data
        File dir = new File(dir_url.toURI());
        for (String dirName: dir.list()) {
            File file = new File(ClassLoader.getSystemResource(dataDirectoryName + File.separator + dirName).toURI());
            if (file.isDirectory()) {
                classifiers.put(file.getName(), file.list());
            }
        }
        // key="haarcascades" value=all files in the directory.
        return classifiers;
    }

    public static String stackTraceToString(Exception ex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pwWriter = new PrintWriter(baos, true);
        ex.printStackTrace(pwWriter);
        return baos.toString();
    }
}
