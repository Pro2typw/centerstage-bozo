package org.firstinspires.ftc.teamcode.vision.test;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class EOCVTest extends OpenCvPipeline {

    Telemetry telemetry;
    private String location = "nothing";
    Mat mat = new Mat();
    public int tolerance = 50;

//    color 1
    private Scalar selectedValue = new Scalar(231, 130, 125);
    private double[] values = selectedValue.val;
    private Scalar lower = new Scalar(values[0] - tolerance < 0? 0: values[0] - tolerance,values[1] - tolerance < 0? 0: values[1] - tolerance, values[2] - tolerance < 0? 0: values[2] - tolerance);
    private Scalar upper = new Scalar(values[0] + tolerance > 255? 255: values[0] + tolerance,values[1] + tolerance > 255? 255: values[1] + tolerance, values[2] + tolerance > 255? 255: values[2] + tolerance);

    private Mat cvtMat = new Mat();
    private Mat binaryMat = new Mat();
    private Mat resultMat = new Mat();

    public EOCVTest(Telemetry telemetry) {
        this.telemetry = telemetry;

    }


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, cvtMat, Imgproc.COLOR_RGB2YCrCb);
        Core.inRange(cvtMat, lower, upper, binaryMat);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(binaryMat, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//        Imgproc.drawContours(resultMat, contours, -1, new Scalar(255,0,0));
//        double maxArea = 0;
//        MatOfPoint biggestContour = null;
//        for(MatOfPoint contour : contours) {
//            double area = Imgproc.contourArea(contour);
//            if(area > maxArea) {
//                biggestContour = contour;
//                maxArea = area;
//            }
//        }
//
//        Rect rect = Imgproc.boundingRect(biggestContour);
//
//        Imgproc.rectangle(resultMat, rect, new Scalar(255,0,0));
        Core.bitwise_and(input, input, resultMat, binaryMat);


        return resultMat;
    }
}
