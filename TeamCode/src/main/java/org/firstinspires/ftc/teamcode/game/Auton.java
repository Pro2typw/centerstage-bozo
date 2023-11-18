package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Hang;

@Autonomous(name = "Auton")
public class Auton extends LinearOpMode {

    private enum States {
        STATE0,
        STATE1,
        STATE2,
        StTATE3
    }

    MecanumDrive mecanumDrive;
    Hang hang;
    States states = States.STATE1;

    double opModeInitRuntime;


    @Override
    public void runOpMode() throws InterruptedException {

        mecanumDrive = new MecanumDrive(hardwareMap);
        hang = new Hang(hardwareMap);

        while(opModeInInit()) {
            opModeInitRuntime = getRuntime();
        }

        waitForStart();

        while(opModeIsActive()) {
            if(getRuntime() - opModeInitRuntime < 1) {
                mecanumDrive.setMotorPowers(1, 1, 1, 1);
            } else if(getRuntime() - opModeInitRuntime < 2) {
                mecanumDrive.setMotorPowers(1, 1, 1, 1);
            } else if(getRuntime() - opModeInitRuntime < 2) {
                mecanumDrive.setMotorPowers(1, 1, 1, 1);
            } else if(getRuntime() - opModeInitRuntime < 2) {
                mecanumDrive.setMotorPowers(1, 1, 1, 1);
            } else if(getRuntime() - opModeInitRuntime < 2) {
                mecanumDrive.setMotorPowers(1, 1, 1, 1);
            }

        }
    }
}
