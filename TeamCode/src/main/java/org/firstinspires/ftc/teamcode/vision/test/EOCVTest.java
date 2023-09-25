package org.firstinspires.ftc.teamcode.vision.test;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

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

    private Mat hsvMat = new Mat();
    private Mat binaryMat = new Mat();
    private Mat resultMat = new Mat();

    public EOCVTest(Telemetry telemetry) {
        this.telemetry = telemetry;

    }


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2YCrCb);
        Core.inRange(hsvMat, lower, upper, binaryMat);
        Core.bitwise_and(input, input, resultMat, binaryMat);
        hsvMat.release();
        binaryMat.release();
        input.release();


        return resultMat;
    }
}
