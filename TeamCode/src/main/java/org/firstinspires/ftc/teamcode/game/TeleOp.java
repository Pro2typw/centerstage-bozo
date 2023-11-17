package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Hang;


public class TeleOp extends LinearOpMode {  

    MecanumDrive mecanumDrive;
    Hang hang = new Hang(hardwareMap);
    Launcher launcher = new Launcher(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {

        mecanumDrive = new MecanumDrive(hardwareMap);

        while(opModeInInit()) {


        }

        waitForStart();

        while (opModeIsActive()) {

            //Code for hanging in endgame:
            if(gamepad2.a) mecanumDrive.toggleDrivetrainCentric();
            mecanumDrive.setToggleMotorPowers(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, (Double x) -> {
                return Math.pow(x, 3);
            });

            if(gamepad2.a)
                hang.stateHang(Hang.StateHang.EXTEND);
            if(gamepad2.b)
                hang.stateHang(Hang.StateHang.RETRACT);

            //Code for airplane launcher:

            if(gamepad1.a)
                launcher.launch();
            else
                launcher.stop();

            //Code for Turret:



            // if driver 1 presses a button - make it reset imu position





        }

    }


}
