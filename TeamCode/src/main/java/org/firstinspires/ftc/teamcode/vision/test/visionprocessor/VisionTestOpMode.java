package org.firstinspires.ftc.teamcode.vision.test.visionprocessor;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "Vision Test Op Mode", group = "VISION TEST")
public class VisionTestOpMode extends LinearOpMode {

    private VisionPortal portal;
    private VisionTest visionTest;

    @Override
    public void runOpMode() throws InterruptedException {
        visionTest = new VisionTest();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcame 1"))
                .setCameraResolution(new Size(640, 480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(visionTest    )
                .build();

        waitForStart();
        telemetry.addData("Prop Position", visionTest.getPropPosition());
        telemetry.update();
    }
}
