package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class TeleOp extends LinearOpMode {

    MecanumDrive mecanumDrive;

    @Override
    public void runOpMode() throws InterruptedException {

        mecanumDrive = new MecanumDrive(hardwareMap);

        while(opModeInInit()) {


        }

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad2.a) mecanumDrive.toggleDrivetrainCentric();
            mecanumDrive.setToggleMotorPowers(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, (Double x) -> {
                return Math.pow(x, 3);
            });
        }

    }
}
