package org.firstinspires.ftc.teamcode.game;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.util.Constants;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.pipelines.RedPropDetection;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

@Autonomous(name = "Red Backdrop", group = "LM2")
public class RedBackdropVisionAuton extends LinearOpMode {
// DONE
    VisionPortal portal;
    RedPropDetection redPropDetection;
    WebcamName webcamName;
    TeamPropLocation location;

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        claw.setLeftClawState(Claw.ClawState.CLOSE);
        claw.setRightClawState(Claw.ClawState.CLOSE);

        Arm arm = new Arm(hardwareMap, slides);

        final Pose2d startPose = new Pose2d(12, -72+11.2, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(14, -35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                    claw.setRightClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d( 25, -35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                    arm.setState(Arm.ArmState.DEPOSIT);
                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                })
                .lineToLinearHeading(new Pose2d(48, -27, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .strafeRight(5)
                .splineToLinearHeading(new Pose2d(59, -56), Math.toRadians(0))
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .lineTo(new Vector2d(12, -35))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                    claw.setRightClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d( 25, -35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                    arm.setState(Arm.ArmState.DEPOSIT);
                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                })
                .lineToLinearHeading(new Pose2d(48, -29, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .strafeRight(5)
                .splineToLinearHeading(new Pose2d(59, -56), Math.toRadians(0))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(44, -36, Math.toRadians(180)), Math.toRadians(90))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                    claw.setRightClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    // Extend slides
                    arm.setState(Arm.ArmState.DEPOSIT);
                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(50, -40, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .strafeRight(5)
                .splineToLinearHeading(new Pose2d(59, -56), Math.toRadians(0))
                .build();

        redPropDetection = new RedPropDetection();

//        portal = new VisionPortal.Builder()
//                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .setCameraResolution(new Size(640, 480))  // you can go up to 720 x 960
//                .setCamera(BuiltinCameraDirection.FRONT)
//                .addProcessor((VisionProcessor) redPropDetection) // TODO: convert to vision processor
//                .build();

        while(opModeInInit()) {
            location = redPropDetection.getPropPosition();
            telemetry.addData("Vision Location", location);
            telemetry.update();
        }

        waitForStart();

        arm.setArmPosition(Constants.Arm.ARM_DEPOSIT_POSITION);

        location = TeamPropLocation.LEFT;
        telemetry.addData("Vision Location", location);
        telemetry.update();

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
