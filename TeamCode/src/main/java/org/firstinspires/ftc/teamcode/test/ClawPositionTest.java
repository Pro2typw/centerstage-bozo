package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "Claw Position Test", group = "Test")
public class ClawPositionTest extends LinearOpMode {
    Claw claw;
    JustPressed gp;
    @Override
    public void runOpMode() throws InterruptedException {
        claw = new Claw(hardwareMap);

        telemetry.addData("Right Claw Position", claw.getRightClawPosition());
        telemetry.addData("Left Claw Position", claw.getLeftClawPosition());
        telemetry.update();

        gp = new JustPressed(gamepad1);

        waitForStart();

        while(opModeIsActive()) {
            if(gp.left_bumper()) claw.inverseLeftClawState();
            if(gp.right_bumper()) claw.inverseRightClawState();
            if(gp.a()){
                claw.inverseRightClawState();
                claw.inverseLeftClawState();
            }

            claw.setLeftClawPosition(claw.getLeftClawPosition() + gp.left_stick_x() * .0001);
            claw.setRightClawPosition(claw.getRightClawPosition() + gp.right_stick_x() * .0001);

            telemetry.addData("Right Claw Position", claw.getRightClawPosition());
            telemetry.addData("Left Claw Position", claw.getLeftClawPosition());
            telemetry.update();
            gp.update();
        }
    }
}
