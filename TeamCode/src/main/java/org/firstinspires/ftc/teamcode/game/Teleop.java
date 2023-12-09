package org.firstinspires.ftc.teamcode.game;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.util.Constants;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Config(value = "LM2 Teleop")
@TeleOp(name = "LM2 Teleop", group = "LM2 Game")
public class Teleop  extends LinearOpMode {
    MecanumDrive drive;
    Launcher launcher;
    Slides slides;
    Claw claw;
    Arm arm;

    public double power = 4;

    boolean dtSlowMode = false;

    HangState hangState = HangState.DOWN;

    JustPressed jpgamepad1, jpgamepad2;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        jpgamepad1 = new JustPressed(gamepad1);
        jpgamepad2 = new JustPressed(gamepad2);



        drive = new MecanumDrive(hardwareMap);
        launcher = new Launcher(hardwareMap);
        slides = new Slides(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap, slides);

        telemetry.addLine("Good luck!");
        telemetry.addLine("Especially our \"Special\" Wiring Lead.");

        waitForStart();
        while(opModeIsActive()) {
            if(dtSlowMode) {
                drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), -jpgamepad1.left_stick_y(), jpgamepad1.right_stick_x(), x -> (Math.pow(x, power) * .3 * (Math.abs(x)/x)));
            }
            else {
                drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), -jpgamepad1.left_stick_y(), jpgamepad1.right_stick_x(), x -> (Math.pow(x, power) * .65 * (Math.abs(x)/x)));
            }
            // TODO: Get better multiplier Sahas; and change apply function?

            // align vertically (for backdrop)
            if (jpgamepad1.a())
                drive.turnAsync(-Angle.normDelta(drive.getPoseEstimate().getHeading()));

            if(jpgamepad1.a()) launcher.switchState(); // TODO: button config

            if(jpgamepad2.dpad_up()) slides.incrementStep(Slides.IncrementDirection.UP_MAJOR);
            if(jpgamepad2.dpad_down()) slides.incrementStep(Slides.IncrementDirection.DOWN_MAJOR);
            if(jpgamepad2.dpad_right()) slides.incrementStep(Slides.IncrementDirection.UP_MINOR);
            if(jpgamepad2.dpad_left()) slides.incrementStep(Slides.IncrementDirection.DOWN_MINOR);
            if(jpgamepad2.x()) slides.setPositionToPreviousStep(Constants.Slides.MAX_POWER);
            if(jpgamepad2.a()) slides.setPositionToBottom();

            if(jpgamepad1.left_bumper() || jpgamepad2.left_bumper()) claw.inverseLeftClawState();
            if(jpgamepad1.right_bumper() || jpgamepad2.right_bumper()) claw.inverseRightClawState();
            if(jpgamepad1.b() || jpgamepad2.b()) {
                claw.inverseLeftClawState();
                claw.inverseRightClawState();
            }
            if(slides.getCurrentPosition() < 50) arm.setState(Arm.ArmState.INTAKE);
            else arm.setState(Arm.ArmState.DEPOSIT);

            if(jpgamepad1.y()) dtSlowMode = !dtSlowMode;

            if(jpgamepad1.x()) {
                if(hangState == HangState.DOWN) {
                    slides.setTargetPosition(Constants.Slides.SLIDE_INIT_HANG_POSITION, Constants.Slides.MAX_POWER);
                    hangState = HangState.DOWN;
                }
                else if(hangState == HangState.UP) {
                    slides.setTargetPosition(Constants.Slides.SLIDE_CLIMB_HANG_POSITION, Constants.Slides.MAX_POWER);
                    hangState = HangState.DONE;
                }
            }

            telemetry.addData("Pose Estimate", drive.getPoseEstimate());
            telemetry.addData("Slow Mode", dtSlowMode);
            telemetry.addData("Left Claw", claw.getLeftClawState());
            telemetry.addData("Right Claw", claw.getRightClawState());
            telemetry.addData("Slide Position", slides.getCurrentPosition());
            telemetry.addData("Average Motor Speed", drive.getAverageWheelVelocity());
            telemetry.update();

            jpgamepad1.update();
            jpgamepad2.update();

        }

    }

    public enum HangState {
        DOWN,
        UP,
        DONE
    }
}