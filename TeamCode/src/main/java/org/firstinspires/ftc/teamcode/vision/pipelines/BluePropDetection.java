package org.firstinspires.ftc.teamcode.vision.pipelines;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class BluePropDetection implements VisionProcessor {

    private Mat testMat = new Mat();
    private Mat finalMat = new Mat();
    private final Scalar rectColor = new Scalar(255, 0, 0);

//    Telemetry telemetry;

    private TeamPropLocation output = TeamPropLocation.LEFT;

    private static final Rect LEFT_RECTANGLE = new Rect(
            new Point(0, 200),
            new Point(200, 400)
    );

    private static final Rect CENTER_RECTANGLE = new Rect(
            new Point(200, 200),
            new Point(400, 400)
    );

    private static final Rect RIGHT_RECTANGLE = new Rect(
            new Point(400, 200),
            new Point(600, 400)
    );

    private double tolerance = 30;
    private final Scalar selectedValue = new Scalar(106, 144, 186);
    private double[] values = selectedValue.val;
    private Scalar lower = new Scalar(values[0] - tolerance < 0? 0: values[0] - tolerance,values[1] - tolerance < 0? 0: values[1] - tolerance, values[2] - tolerance < 0? 0: values[2] - tolerance);
    private Scalar upper = new Scalar(values[0] + tolerance > 255? 255: values[0] + tolerance,values[1] + tolerance > 255? 255: values[1] + tolerance, values[2] + tolerance > 255? 255: values[2] + tolerance);

//    public BluePropDetection(Telemetry telemetry) {
//        this.telemetry = telemetry;
//    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(testMat, lower, upper, finalMat);
        testMat.release();

        double leftBox = Core.sumElems(finalMat.submat(LEFT_RECTANGLE)).val[0];
        double centerBox = Core.sumElems(finalMat.submat(CENTER_RECTANGLE)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];

        double averagedLeftBox = leftBox / LEFT_RECTANGLE.area() / 255;
        double averagedCenterBox = centerBox / RIGHT_RECTANGLE.area() / 255;
        double averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255;

        double max = Math.max(Math.max(averagedLeftBox, averagedCenterBox), averagedRightBox);
        if(max == averagedLeftBox) output = TeamPropLocation.LEFT;
        else if(max == averagedCenterBox) output = TeamPropLocation.CENTER;
        else output = TeamPropLocation.RIGHT;





        finalMat.copyTo(frame);
        Imgproc.rectangle(frame, LEFT_RECTANGLE, new Scalar(255, 255, 0));
        Imgproc.rectangle(frame, CENTER_RECTANGLE, rectColor);
        Imgproc.rectangle(frame, RIGHT_RECTANGLE, rectColor);

//        telemetry.addData("Output", getPropPosition());
//        telemetry.addData("left", averagedLeftBox);
//        telemetry.addData("center", averagedCenterBox);
//        telemetry.addData("right", averagedRightBox);
//        telemetry.update();

        return null;
    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public TeamPropLocation getPropPosition(){
        return output;
    }

}
