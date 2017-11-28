package org.opencv.demo.core;

//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.demo.misc.Constants;
import org.opencv.demo.misc.ImageUtils;
import org.opencv.demo.misc.Loggable;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.*;

public class RecognizerManager {

    private final List images;
    private final MatOfInt labelsBuffer;
    private FaceRecognizer faceRecognizer;
    private Loggable logger;
    Map<Integer, String> idToNameMapping = null;
    private int[] labels;
    private double[] confidence;



    public RecognizerManager(Loggable logger) throws Exception {
        this.logger = logger;

//        URL dir_url = ClassLoader.getSystemResource(Constants.TRAINING_FACES_PATH);
//        File trainingDir = new File(dir_url.toURI());
        // /Users/rocky/Documents/faces
        File trainingDir = new File(Constants.FACES_FILE_PATH);

        /**
         * put the files ended with jpg, png, pgm into a File[]
         */
        File[] imageFiles = getImagesFiles(trainingDir);
        /**
         * key: sequence number, value: name. e.g. <1, ivysun> <2, junxuez> ...
         */
        idToNameMapping = createSummary(imageFiles);
        /**
         * Mat [ 108*1*CV_32SC1, isCont=true, isSubmat=false, nativeObj=0x7ff239f472c0, dataAddr=0x7ff239f47960 ]
         */
        labelsBuffer = new MatOfInt(new int[imageFiles.length]);

        /**
         * store the training image in grayscale
         */
        images = new ArrayList(imageFiles.length);
        int counter = 0;
        for (File image : imageFiles) {
            // reads the training image in grayscale
            Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

            // gets the id of this image
            int labelId = getIdFromImage(image.getName(), idToNameMapping);

            // sets the image
            images.add(img);
            /**
             * what the hell
             */
            labelsBuffer.put(counter++, 0, labelId);
        }

        // set the recognizer
        faceRecognizer = RecognizerFactory.getRecognizer(RecognizerType.FISHER);
        /**
         *
         */
        trainRecognizer();
        labels = new int[idToNameMapping.size()];
        confidence = new double[idToNameMapping.size()];
    }

    public void trainRecognizer() {
        faceRecognizer.train(images, labelsBuffer);
    }

    public void changeRecognizer(FaceRecognizer faceRecognizer) {

        this.faceRecognizer = faceRecognizer;
        trainRecognizer();
    }

    public RecognizedFace recognizeFace(Mat face) {

        if (face == null) {
            return Constants.UNKNOWN_FACE;
        }

        Mat resizedGrayFace = ImageUtils.toGrayScale(ImageUtils.resizeFace(face));
        faceRecognizer.predict(resizedGrayFace, labels, confidence);

        if (confidence[0] < Constants.FACE_RECOGNITION_THRESHOLD) {
            return new RecognizedFace(idToNameMapping.get(labels[0]), confidence[0]);
        }

        System.out.println("unkonwn");
        return Constants.UNKNOWN_FACE;
    }

    private File[] getImagesFiles(File trainingDir) {

        FilenameFilter imgFilter = (dir, name) -> {
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
        };

        return trainingDir.listFiles(imgFilter);
    }

    private int getIdFromImage(String filename, Map<Integer, String> idToNameMapping) {
        String name = filename.split("_")[0];
        return idToNameMapping.keySet()
                .stream()
                .filter(id -> idToNameMapping.get(id).equals(name))
                .findFirst()
                .orElse(-1);
    }

    private Map<Integer, String> createSummary(File[] imagesFiles) {

        Map<Integer, String> idToNameMapping = new HashMap<>();
        int idCounter = 0;
        for (File imageFile : imagesFiles) {
            String name = imageFile.getName().split("_")[0];
            if (!idToNameMapping.values().contains(name)) {
                idToNameMapping.put(idCounter++, name);
            }
        }

        return idToNameMapping;
    }

}
