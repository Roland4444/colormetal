package ru.com.avs.thread;

import static ru.com.avs.util.Utils.onFxThread;

import java.io.File;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.com.avs.service.PropertyService;
import ru.com.avs.util.Utils;

@Component("CameraThread")
@Scope("prototype")
public class CameraThread extends Thread {
    Mat frame___ = new Mat();
    Size size1 = new Size(350, 350);
    Size size2 = new Size(50, 50);
    @FXML
    private ImageView videoFrame;
    @FXML
    private String cameraUrl;

    private boolean isActive;

    @Autowired
    private PropertyService property;
    private VideoCapture capture;
    private String nameFile;
    private boolean isSnapshot = false;

    /**
     * Constructor.
     *
     * @param videoFrame ImageView
     * @param cameraUrl  String
     */
    public CameraThread(ImageView videoFrame, String cameraUrl) {
        super();
        this.isActive = true;
        this.videoFrame = videoFrame;
        this.cameraUrl = cameraUrl;
    }

    @Override
    public void run() {
        this.capture = new VideoCapture();

        if (this.capture.open(cameraUrl)) {
            while (isActive) {
                Mat frame = grabFrame();
                if (!frame.empty()) {
                    if (isSnapshot) {
                        createSnapshot(frame);
                    }
                    Imgproc.resize(frame, frame, size1);
                    Image imageToShow = Utils.mat2Image(frame);
                    onFxThread(videoFrame.imageProperty(), imageToShow);
                    frame.release();
                }
            }
        } else {
            videoFrame.setImage(
                    new Image(String.valueOf(ClassLoader.getSystemResource("icon/border350.png"))));
        }
    }

    /**
     * For getting snapshot.
     *
     * @param nameFile identity of snapshot
     */
    public void getSnapshot(String nameFile) {
        this.nameFile = nameFile;
        this.isSnapshot = true;
    }

    /**
     * Stopping thread.
     */
    public void stopThread() {
        isActive = false;
        do {
            if (!this.isAlive()) {
                capture.release();
                this.interrupt();
            }
        } while (this.isAlive());
    }

    private void createSnapshot(Mat frame) {
        if (property.getBoolProperty("camera.isBlackWhite")) {
            Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
        }
        String basePath = property.getProperty("image.path") + LocalDate.now() + "/";
        File folder = new File(basePath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String fullImage = basePath + "snapshot-" + nameFile + ".jpg";
        Imgcodecs.imwrite(fullImage, frame);


        Imgproc.resize(frame, frame___, size1);
        String preview = basePath + "previewOur-" + nameFile + ".jpg";
        Imgcodecs.imwrite(preview, frame___);

        Imgproc.resize(frame, frame___, size2);
        preview = basePath + "preview-" + nameFile + ".jpg";
        Imgcodecs.imwrite(preview, frame___);

        frame___.release();
        isSnapshot = false;
    }

    private Mat grabFrame() {
        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame___);
            } catch (Exception e) {
                // log.error("Exception during the image elaboration: ", e);
            }
        }
        return frame___;
    }
}
