package org.firstinspires.ftc.teamcode.vision.test;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class YCbCrTest extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();


    public YCbCrTest(Telemetry telemetry) {
        this.telemetry = telemetry;

    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2YCrCb);
        return mat;
    }
}
