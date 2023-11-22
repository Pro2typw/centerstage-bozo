package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "Test Teleop")
public class TeleopGame extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive;
        CRServo launch;
        drive = new MecanumDrive(hardwareMap);
        launch = hardwareMap.get(CRServo.class, "servo");

        waitForStart();

        while (opModeIsActive()) {
            drive.setPowersByGamepadRobotCentric(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, .7, (x) -> x);
            if(gamepad2.a) {
                launch.setDirection(DcMotorSimple.Direction.FORWARD);
                launch.setPower(1);
            }


        }

    }
}
