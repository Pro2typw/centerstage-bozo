package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.tools.javac.jvm.Code;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.AirplaneLauncher;
import org.firstinspires.ftc.teamcode.subsystems.Hang;


public class TeleOp extends LinearOpMode {  

    MecanumDrive mecanumDrive;
    Hang hang = new Hang(hardwareMap);
    AirplaneLauncher launcher = new AirplaneLauncher(hardwareMap);
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
        }

    }


}
