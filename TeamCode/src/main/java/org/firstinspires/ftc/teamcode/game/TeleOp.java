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

        }

    }
}
