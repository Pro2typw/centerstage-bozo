package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "Claw Position Test", group = "Test")
public class ClawPositionTest extends LinearOpMode {

    Servo claw;

    JustPressed gp;
    @Override
    public void runOpMode() throws InterruptedException {
        claw = hardwareMap.get(Servo.class, "rightClaw");

        telemetry.addData("Right Claw Position", claw.getPosition());
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.dpad_right) claw.setPosition(claw.getPosition() + .05);
            if(gamepad1.dpad_left) claw.setPosition((claw.getPosition() - .05));

            if(gamepad1.right_bumper) claw.setPosition((claw.getPosition() + .01));
            if(gamepad1.left_bumper) claw.setPosition((claw.getPosition() - .01));

            telemetry.addData("Right Claw Position", claw.getPosition());
            telemetry.update();
        }
    }
}
