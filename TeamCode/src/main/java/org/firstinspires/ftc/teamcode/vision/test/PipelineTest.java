package org.firstinspires.ftc.teamcode.vision.test;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.pipelines.RedPropDetection;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

@TeleOp(group = "Test", name = "Pipeline Test")
public class PipelineTest extends LinearOpMode {
    VisionPortal portal;
    RedPropDetection pipeline;
    WebcamName webcamName;
    TeamPropLocation location;
    @Override
    public void runOpMode() throws InterruptedException {

        pipeline = new RedPropDetection();

        portal = new VisionPortal.Builder()
                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))  // you can go up to 720 x 960
                .setCamera(BuiltinCameraDirection.FRONT)
                .addProcessor((VisionProcessor) pipeline) // TODO: convert to vision processor
                .build();

        location = pipeline.getPropPosition();
        telemetry.addData("location", location);
        telemetry.update();

        waitForStart();


    }
}
