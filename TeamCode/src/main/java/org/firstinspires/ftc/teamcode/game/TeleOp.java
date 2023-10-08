package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Hang;

public class TeleOp extends LinearOpMode {

    MecanumDrive mecanumDrive;
    Hang hang = new Hang(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {

        mecanumDrive = new MecanumDrive(hardwareMap);

        while(opModeInInit()) {

        }

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.a)
                hang.stateHang(Hang.StateHang.EXTEND);
            if(gamepad1.b)
                hang.stateHang(Hang.StateHang.RETRACT);
        }

    }


}
