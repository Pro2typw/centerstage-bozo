package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class TeleOp extends LinearOpMode {
    public enum ToggleRobotCentric {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }
    MecanumDrive mecanumDrive;
    ToggleRobotCentric drivetrainCentric;

    @Override
    public void runOpMode() throws InterruptedException {

        mecanumDrive = new MecanumDrive(hardwareMap);
        drivetrainCentric = ToggleRobotCentric.ROBOT_CENTRIC;

        while(opModeInInit()) {


        }

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad2.a) {
                drivetrainCentric = drivetrainCentric == ToggleRobotCentric.ROBOT_CENTRIC ? ToggleRobotCentric.FIELD_CENTRIC : ToggleRobotCentric.ROBOT_CENTRIC;
            }

            switch (drivetrainCentric) {
                case FIELD_CENTRIC:
                    mecanumDrive.setPowersByGamepadFieldCentric(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, (Double x) -> {
                        return Math.pow(x, 3);
                    });
                    break;
                case ROBOT_CENTRIC:
                    mecanumDrive.setPowersByGamepadRobotCentric(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, (Double x) -> {
                        return Math.pow(x, 3);
                    });
                    break;
            }
        }

    }
}
