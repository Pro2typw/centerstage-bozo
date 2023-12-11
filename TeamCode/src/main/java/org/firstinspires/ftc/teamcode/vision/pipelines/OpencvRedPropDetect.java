package org.firstinspires.ftc.teamcode.vision.pipelines;

import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class OpencvRedPropDetect extends OpenCvPipeline {

    private Mat testMat = new Mat();
    private Mat finalMat = new Mat();
    private final Scalar rectColor = new Scalar(255, 0, 0);


    private TeamPropLocation output = TeamPropLocation.LEFT;

    private static final Rect CENTER_RECTANGLE = new Rect(
            new Point(170, 360),
            new Point(565, 600)
    );

    private static final Rect RIGHT_RECTANGLE = new Rect(
            new Point(730, 330),
            new Point(960, 600)
    );

    private double tolerance = 60;
    private final Scalar selectedValue = new Scalar(5, 185, 128);
    private double[] values = selectedValue.val;
    private Scalar lower = new Scalar(values[0] - tolerance < 0? 0: values[0] - tolerance,values[1] - tolerance < 0? 0: values[1] - tolerance, values[2] - tolerance < 0? 0: values[2] - tolerance);
    private Scalar upper = new Scalar(values[0] + tolerance > 255? 255: values[0] + tolerance,values[1] + tolerance > 255? 255: values[1] + tolerance, values[2] + tolerance > 255? 255: values[2] + tolerance);
    @Override
    public Mat processFrame(Mat frame) {
        Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);

        Core.inRange(testMat, lower, upper, finalMat);
        testMat.release();

        double centerBox = Core.sumElems(finalMat.submat(CENTER_RECTANGLE)).val[0];
        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];

        double averagedCenterBox = centerBox;
        double averagedRightBox = rightBox;

        double max = Math.max(averagedRightBox, averagedCenterBox);
        if(max < .1) output = TeamPropLocation.LEFT;
        else if(max == averagedCenterBox) output = TeamPropLocation.CENTER;
        else output = TeamPropLocation.RIGHT;

        Imgproc.rectangle(frame, CENTER_RECTANGLE, rectColor);
        Imgproc.rectangle(frame, RIGHT_RECTANGLE, rectColor);


        return frame;
    }
    public TeamPropLocation getPropPosition(){
        return output;
    }
}
