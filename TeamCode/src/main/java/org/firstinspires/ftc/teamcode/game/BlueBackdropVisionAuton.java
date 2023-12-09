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
import org.firstinspires.ftc.teamcode.util.TelemetryUtil;
import org.firstinspires.ftc.teamcode.vision.pipelines.BluePropDetection;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

@Autonomous(group = "LM2 Blue Backdrop")
public class BlueBackdropVisionAuton extends LinearOpMode {
    // DONE

    VisionPortal portal;
    BluePropDetection bluePropDetection;
    WebcamName webcamName;
    TeamPropLocation location;
    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumDrive(hardwareMap);

        final Pose2d startPose = new Pose2d(12, 72-11.2, Math.toRadians(90));

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(12, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                })
                .lineToLinearHeading(new Pose2d(46, 35, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                })
                .strafeLeft(5)
                .splineToLinearHeading(new Pose2d(59, 60), Math.toRadians(0))
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .lineTo(new Vector2d(12, 35))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                })
                .lineToLinearHeading(new Pose2d(46, 35, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                })
                .strafeLeft(5)
                .splineToLinearHeading(new Pose2d(59, 60), Math.toRadians(0))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(36, 36, Math.toRadians(0)), Math.toRadians(270))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                })
                .addDisplacementMarker(() -> {
                    // Extend slides
                })
                .lineToLinearHeading(new Pose2d(46, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                })
                .strafeRight(5)
                .splineToLinearHeading(new Pose2d(59, 60, Math.toRadians(180)), Math.toRadians(0))
                .build();

        bluePropDetection = new BluePropDetection();



        portal = new VisionPortal.Builder()
                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))  // you can go up to 720 x 960
                .setCamera(BuiltinCameraDirection.FRONT)
                .addProcessor((VisionProcessor) bluePropDetection) // TODO: convert to vision processor
                .build();

        telemetry = TelemetryUtil.initTelemetry(telemetry);

        while(opModeInInit()) {
            location = bluePropDetection.getPropPosition();
            telemetry.addData("Vision Location", location.toString());
            telemetry.update();
        }

        waitForStart();


        switch (location) {
            case LEFT:
                drive.followTrajectorySequenceAsync(left);
                break;
            case CENTER:
                drive.followTrajectorySequenceAsync(center);
                break;
            case RIGHT:
                drive.followTrajectorySequenceAsync(right);
        }

        while (opModeIsActive()) {
            drive.update();
            telemetry.addData("pose estimate", drive.getPoseEstimate());
            telemetry.update();
        }

    }
}
