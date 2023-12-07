package org.firstinspires.ftc.teamcode.test;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@Config
@TeleOp(name = "Arm Test", group = "Test")
public class ArmPositionTest extends LinearOpMode {


    public static double armPos = 0.9233333333333333;
    public static double wristPos = 0.8694444444444445;
    JustPressed jpgamepad1, jpgamepad2;
    @Override
    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        Slides slides = new Slides(hardwareMap);
        Arm arm = new Arm(hardwareMap, slides);

        jpgamepad1 = new JustPressed(gamepad1);
        jpgamepad2 = new JustPressed(gamepad2);

        while (opModeInInit()) {
            telemetry.addData("arm", arm.getArmPosition());
            telemetry.addData("wrist", arm.getWristPosition());
            telemetry.addData("slides", slides.getCurrentPosition());
            telemetry.update();

            arm.setArmPosition(armPos);
            arm.setWristPosition(wristPos);
        }

        waitForStart();
        while(opModeIsActive()) {
            if(jpgamepad2.dpad_up()) slides.incrementStep(Slides.IncrementDirection.UP_MAJOR); // TODO: button config
            if(jpgamepad2.dpad_down()) slides.incrementStep(Slides.IncrementDirection.DOWN_MAJOR); // TODO: button config
            if(jpgamepad2.dpad_right()) slides.incrementStep(Slides.IncrementDirection.UP_MINOR); // TODO: button config
            if(jpgamepad2.dpad_left()) slides.incrementStep(Slides.IncrementDirection.DOWN_MINOR); // TODO: button config

            arm.setArmPosition(arm.getArmPosition() + gamepad1.right_stick_x * .001);
            arm.setWristPosition(arm.getWristPosition() + gamepad1.left_stick_x * .001);

            telemetry.addData("arm", arm.getArmPosition());
            telemetry.addData("wrist", arm.getWristPosition());
            telemetry.addData("slides", slides.getCurrentPosition());
            telemetry.update();
            jpgamepad1.update();
            jpgamepad2.update();
        }
    }
}
