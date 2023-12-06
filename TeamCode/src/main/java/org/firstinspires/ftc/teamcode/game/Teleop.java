package org.firstinspires.ftc.teamcode.game;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp(name = "LM2 Teleop", group = "LM2 Game")
public class Teleop  extends LinearOpMode {

    MecanumDrive drive;
    Launcher launcher;
    Slides slides;
    Claw claw;
    Arm arm;

    boolean dtSlowMode = false;

    JustPressed jpgamepad1, jpgamepad2;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        jpgamepad1 = new JustPressed(gamepad1);
        jpgamepad2 = new JustPressed(gamepad2);



        drive = new MecanumDrive(hardwareMap);

//        uncomment for launcher
//        launcher = new Launcher(hardwareMap);
        slides = new Slides(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap, slides);


        waitForStart();
        while(opModeIsActive()) {
            if(dtSlowMode) drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), jpgamepad1.left_stick_y(), -jpgamepad1.right_stick_x(), .3);
            else drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), jpgamepad1.left_stick_y(), -jpgamepad1.right_stick_x(), .75);
            // TODO: Get better multiplier Sahas; and change apply function?
//`         uncomment for launcher
//            if(jpgamepad2.a()) launcher.switchState(); // TODO: button config
            if(jpgamepad2.dpad_up()) slides.incrementStep(Slides.IncrementDirection.UP_MAJOR); // TODO: button config
            if(jpgamepad2.dpad_down()) slides.incrementStep(Slides.IncrementDirection.DOWN_MAJOR); // TODO: button config
            if(jpgamepad2.dpad_right()) slides.incrementStep(Slides.IncrementDirection.UP_MINOR); // TODO: button config
            if(jpgamepad2.dpad_left()) slides.incrementStep(Slides.IncrementDirection.DOWN_MINOR); // TODO: button config
            if(jpgamepad2.x()) slides.setPositionToPreviousStep(); // TODO: button config
            if(jpgamepad2.a()) slides.setPositionToBottom();
            if(jpgamepad1.left_bumper()) claw.inverseLeftClawState(); // TODO: button config
            if(jpgamepad1.right_bumper()) claw.inverseRightClawState(); // TODO: button config
            if(jpgamepad1.b()) {
                claw.inverseLeftClawState();
                claw.inverseRightClawState();
            }
            if(slides.getCurrentPosition() < 50) arm.setState(Arm.ArmState.INTAKE);
            else arm.setState(Arm.ArmState.DEPOSIT);
            if(jpgamepad1.y()) dtSlowMode = !dtSlowMode;

            telemetry.addData("Current Slide Position", slides.getCurrentPosition());
            telemetry.addData("Slow Mode", dtSlowMode);
            telemetry.update();

            jpgamepad1.update();
            jpgamepad2.update();

        }
    }
}