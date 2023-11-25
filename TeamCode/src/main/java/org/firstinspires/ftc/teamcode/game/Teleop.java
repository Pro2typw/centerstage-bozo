package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "LM2 Teleop", group = "LM2 Game")
public class Teleop  extends LinearOpMode {
    MecanumDrive drive;
    Launcher launcher;

    JustPressed jpgamepad1, jpgamepad2;
    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumDrive(hardwareMap);
        launcher = new Launcher(hardwareMap);

        jpgamepad1 = new JustPressed(gamepad1);
        jpgamepad2 = new JustPressed(gamepad2);

        waitForStart();
        while(opModeIsActive()) {
            drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), jpgamepad1.left_stick_y(), jpgamepad1.right_stick_x(), x -> (x * .75));
            // TODO: Get better multiplier Sahas; and change apply function?

            if(jpgamepad2.a()) launcher.launch();

        }


    }
}
