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
import org.firstinspires.ftc.teamcode.util.TelemetryUtil;
import org.firstinspires.ftc.teamcode.vision.pipelines.BluePropDetection;
import org.firstinspires.ftc.teamcode.vision.pipelines.OpencvBluePropDetect;
import org.firstinspires.ftc.teamcode.vision.pipelines.OpencvRedPropDetect;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(group = "LM2", name = "Blue Backdrop")
public class BlueBackdropVisionAuton extends LinearOpMode {
    // DONE

    VisionPortal portal;
    OpencvBluePropDetect bluePropDetection;
    WebcamName webcamName;
    TeamPropLocation location;
    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        claw.setLeftClawState(Claw.ClawState.CLOSE);
        claw.setRightClawState(Claw.ClawState.CLOSE);

        Arm arm = new Arm(hardwareMap, slides);

        final Pose2d startPose = new Pose2d(12+5, 72-11.2, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(14, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                    claw.setRightClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(180)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                    arm.setState(Arm.ArmState.DEPOSIT);
                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                })
                .lineToLinearHeading(new Pose2d(52, 27, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(1)
                .back(4)
                .strafeLeft(5)
                .splineToLinearHeading(new Pose2d(59, 56), Math.toRadians(0))
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .lineTo(new Vector2d(12, 35))
                .addDisplacementMarker(() -> {
                    // Place pixel on the ground
                    claw.setRightClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .addDisplacementMarker(() -> {
                    arm.setWristPosition(Constants.Arm.WRIST_DEPOSIT_POSITION);
                })
                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(270)))
                .addDisplacementMarker(() -> {
                    // Extend slides
                    arm.setState(Arm.ArmState.DEPOSIT);
                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                })
                .lineToLinearHeading(new Pose2d(52, 27, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(1)
                .back(4)
                .strafeLeft(5)
                .splineToLinearHeading(new Pose2d(59, 56), Math.toRadians(0))
                .build();

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .splineToSplineHeading(new Pose2d(39, 30, Math.toRadians(180)), Math.toRadians(270))
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
                .lineToLinearHeading(new Pose2d(52, 40, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .waitSeconds(2)
                .back(8)
                .strafeLeft(5)
                .splineToLinearHeading(new Pose2d(59, 56), Math.toRadians(0))
                .build();

        bluePropDetection = new OpencvBluePropDetect();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera cvCamera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        cvCamera.setPipeline(bluePropDetection);
        cvCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                cvCamera.startStreaming(960, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });




        while(opModeInInit()) {
            location = bluePropDetection.getPropPosition();
            telemetry.addData("Vision Location", location.toString());
            telemetry.update();
        }

        waitForStart();

        arm.setArmPosition(Constants.Arm.ARM_DEPOSIT_POSITION);

        location = TeamPropLocation.LEFT;
        telemetry.addData("Vision Location", location);
        telemetry.update();

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

            if(!drive.isBusy() && getRuntime() > 10) {
                slides.setPositionToBottom();
                arm.setState(Arm.ArmState.INTAKE);
            }
        }

    }
}
