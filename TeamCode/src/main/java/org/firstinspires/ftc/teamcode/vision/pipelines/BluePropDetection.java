package org.firstinspires.ftc.teamcode.vision.pipelines;

import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class BluePropDetection extends OpenCvPipeline {
    Mat testMat = new Mat();
    Mat finalMat = new Mat();

    TeamPropLocation output = TeamPropLocation.LEFT;

    private static final Rect LEFT_RECTANGLE = new Rect( // TODO
            new Point(0, 0),
            new Point(0, 0)
    );
    private static final Rect CENTER_RECTANGLE = new Rect( // TODO
            new Point(0, 0),
            new Point(0, 0)
    );
    private static final Rect RIGHT_RECTANGLE = new Rect( // TODO
            new Point(0, 0),
            new Point(0, 0)
    );

    private final Scalar rectColor = new Scalar(0, 0, 255);

    public int tolerance = 30;
    private final Scalar selectedValue = new Scalar(103, 103, 168); // hsv(103, 103, 16)
    private double[] values = selectedValue.val;
    private Scalar lower = new Scalar(values[0] - tolerance < 0? 0: values[0] - tolerance,values[1] - tolerance < 0? 0: values[1] - tolerance, values[2] - tolerance < 0? 0: values[2] - tolerance);
    private Scalar upper = new Scalar(values[0] + tolerance > 255? 255: values[0] + tolerance,values[1] + tolerance > 255? 255: values[1] + tolerance, values[2] + tolerance > 255? 255: values[2] + tolerance);
    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, testMat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(testMat, lower, upper, finalMat);

        double leftBox = Core.sumElems(finalMat.submat(LEFT_RECTANGLE)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];
        double centerBox = Core.sumElems(finalMat.submat(CENTER_RECTANGLE)).val[0];

        double averagedLeftBox = leftBox / LEFT_RECTANGLE.area() / 255;
        double averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255;
        double averagedCenterBox = centerBox / CENTER_RECTANGLE.area() / 255;

        double maxValue = Math.max(Math.max(averagedLeftBox, averagedRightBox), averagedCenterBox);

        if(averagedLeftBox == maxValue) {
            output = TeamPropLocation.LEFT;
            Imgproc.rectangle(input, LEFT_RECTANGLE, new Scalar(255, 255, 255));
            Imgproc.rectangle(input, CENTER_RECTANGLE, rectColor);
            Imgproc.rectangle(input, RIGHT_RECTANGLE, rectColor);
        } else if(averagedRightBox == maxValue) {
            output = TeamPropLocation.RIGHT;
            Imgproc.rectangle(input, LEFT_RECTANGLE, rectColor);
            Imgproc.rectangle(input, CENTER_RECTANGLE, rectColor);
            Imgproc.rectangle(input, RIGHT_RECTANGLE, new Scalar(255, 255, 255));
        } else {
            output = TeamPropLocation.CENTER;
            Imgproc.rectangle(input, LEFT_RECTANGLE, rectColor);
            Imgproc.rectangle(input, CENTER_RECTANGLE, new Scalar(255, 255, 255));
            Imgproc.rectangle(input, RIGHT_RECTANGLE, rectColor);
        }

        finalMat.copyTo(input);
        finalMat.release();

        return input;
    }

    public TeamPropLocation getOutput() {
        return output;
    }
}
