package org.firstinspires.ftc.teamcode.game;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.pipelines.RedTeamPropDetection;
import org.firstinspires.ftc.teamcode.vision.test.visionprocessor.VisionTest;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "Red Vision", group = "LM1 Game")
public class RedVisionAuton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        VisionPortal portal;
        RedTeamPropDetection redTeamPropDetection;
        WebcamName webcamName;

        redTeamPropDetection = new RedTeamPropDetection();
        portal = new VisionPortal.Builder()
                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))  // you can go up to 720 x 960
                .setCamera(BuiltinCameraDirection.FRONT)
                .addProcessor(redTeamPropDetection)
                .build();



        waitForStart();

    }
}
