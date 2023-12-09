package org.firstinspires.ftc.teamcode.game;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.pipelines.RedPropDetection;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

@Autonomous(group = "LM2 Red Stack")
public class RedStackVisionAuton extends LinearOpMode {

    VisionPortal portal;
    RedPropDetection redPropDetection;
    WebcamName webcamName;
    TeamPropLocation location;
    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumDrive(hardwareMap);

        final Pose2d startPose = new Pose2d(-36, -72+11.2, Math.toRadians(90));

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-36, 36, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .lineTo(new Vector2d(-36, 12))
                .lineTo(new Vector2d(60, 12))
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .lineTo(new Vector2d(-36, -36))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .lineTo(new Vector2d(12, -36))
                .splineTo(new Vector2d(60, -12), Math.toRadians(0))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-36, -36, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .lineTo(new Vector2d(-36, -12))
                .lineTo(new Vector2d(60, -12))
                .build();

        redPropDetection = new RedPropDetection();

        portal = new VisionPortal.Builder()
                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))  // you can go up to 720 x 960
                .setCamera(BuiltinCameraDirection.FRONT)
                .addProcessor((VisionProcessor) redPropDetection) // TODO: convert to vision processor
                .build();

        while(opModeInInit()) {
            location = redPropDetection.getPropPosition();
            telemetry.addData("Vision Location", location.toString());
            telemetry.update();
        }

        waitForStart();

        switch (location) {
            case LEFT:
                drive.followTrajectorySequence(left);
                break;
            case CENTER:
                drive.followTrajectorySequence(center);
                break;
            case RIGHT:
                drive.followTrajectorySequence(right);
        }

        while (opModeIsActive()) {
            drive.update();
        }

    }
}
