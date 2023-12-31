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
import org.firstinspires.ftc.teamcode.vision.pipelines.OpencvRedPropDetect;
import org.firstinspires.ftc.teamcode.vision.pipelines.RedPropDetection;
import org.firstinspires.ftc.teamcode.vision.util.TeamPropLocation;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Red Backdrop", group = "LM2")
public class RedBackdropVisionAuton extends LinearOpMode {
// DONE
    VisionPortal portal;
    OpencvRedPropDetect redPropDetection;
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

        final Pose2d startPose = new Pose2d(12+5, -72+11.2, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .strafeLeft(5)
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
                .addDisplacementMarker(() -> {
                    slides.setPositionToBottom();
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .build();

        TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
                .strafeLeft(5)
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
                .addDisplacementMarker(() -> {
                    slides.setPositionToBottom();
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .waitSeconds(2)
//                .strafeLeft(5)
                .splineToSplineHeading(new Pose2d(40, -36, Math.toRadians(180)), Math.toRadians(90))
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
                .lineToLinearHeading(new Pose2d(52, -40, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    // Place pixel on backdrop
                    claw.setLeftClawState(Claw.ClawState.OPEN);
                })
                .strafeRight(5)
                .splineToLinearHeading(new Pose2d(59, -56), Math.toRadians(0))
                .addDisplacementMarker(() -> {
                    slides.setPositionToBottom();
                    arm.setState(Arm.ArmState.INTAKE);
                })
                .build();

        redPropDetection = new OpencvRedPropDetect();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera cvCamera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        cvCamera.setPipeline(redPropDetection);
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
            location = redPropDetection.getPropPosition();
            telemetry.addData("Vision Location", location);
            telemetry.update();
        }

        waitForStart();

        arm.setArmPosition(Constants.Arm.ARM_DEPOSIT_POSITION);


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
            if(!drive.isBusy() && getRuntime() > 10) {
                slides.setPositionToBottom();
                arm.setState(Arm.ArmState.INTAKE);
            }
        }

    }
}
