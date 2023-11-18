package org.firstinspires.ftc.teamcode.vision.pipelines;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropROI;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class RedTeamPropDetection implements VisionProcessor {
    Mat testMat = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();

    TeamPropROI outStr = TeamPropROI.LEFT;

    private final Scalar rectColor = new Scalar(0, 0, 0);
    static final Rect LEFT_RECTANGLE = new Rect(
            new Point(0, 0),
            new Point(0, 0)
    );

    static final Rect RIGHT_RECTANGLE = new Rect(
            new Point(0, 0),
            new Point(0, 0)
    );

    static final Rect CENTER_RECTANGLE = new Rect(
            new Point(0, 0),
            new Point(0, 0)
    );

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);

        Scalar lowHSVRedLower = new Scalar(0, 100, 20);
        Scalar lowHSVRedUpper = new Scalar(10, 255, 255);

        Scalar redHSVRedLower = new Scalar(160, 100, 20);
        Scalar highHSVRedUpper = new Scalar(180, 255, 255);

        Core.inRange(testMat, lowHSVRedLower, lowHSVRedUpper, lowMat);
        Core.inRange(testMat, redHSVRedLower, highHSVRedUpper, highMat);


        testMat.release();

        Core.bitwise_or(lowMat, highMat, finalMat);

        lowMat.release();
        highMat.release();

        double leftBox = Core.sumElems(finalMat.submat(LEFT_RECTANGLE)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];
        double centerBox = Core.sumElems(finalMat.submat(CENTER_RECTANGLE)).val[0];

        double averagedLeftBox = leftBox / LEFT_RECTANGLE.area() / 255;
        double averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255;
        double averagedCenterBox = centerBox / CENTER_RECTANGLE.area() / 255;

        double maxValue = Math.max(Math.max(averagedLeftBox, averagedRightBox), averagedCenterBox);

        if(averagedLeftBox == maxValue) {
            outStr = TeamPropROI.LEFT;
        }
        else if(averagedRightBox == maxValue) {
            outStr = TeamPropROI.RIGHT;
        }
        else {
            outStr = TeamPropROI.CENTER;
        }

        if(averagedLeftBox == maxValue) {
            outStr = TeamPropROI.LEFT;
            Imgproc.rectangle(frame, LEFT_RECTANGLE, new Scalar(255, 255, 255));
            Imgproc.rectangle(frame, CENTER_RECTANGLE, rectColor);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, rectColor);
        }
        else if(averagedRightBox == maxValue) {
            outStr = TeamPropROI.RIGHT;
            Imgproc.rectangle(frame, LEFT_RECTANGLE, rectColor);
            Imgproc.rectangle(frame, CENTER_RECTANGLE, rectColor);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, new Scalar(255, 255, 255));
        }
        else {
            outStr = TeamPropROI.CENTER;
            Imgproc.rectangle(frame, LEFT_RECTANGLE, rectColor);
            Imgproc.rectangle(frame, CENTER_RECTANGLE, new Scalar(255, 255, 255));
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, rectColor);
        }

        finalMat.copyTo(frame);
        finalMat.release();

        return frame;
    }

    public TeamPropROI getPropPosition(){
        return outStr;
    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}
