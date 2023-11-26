package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "LM2 Teleop", group = "LM2 Game")
public class Teleop  extends LinearOpMode {
//    MecanumDrive drive;
//    Launcher launcher;
    Slides slides;

    JustPressed jpgamepad1, jpgamepad2;
    @Override
    public void runOpMode() throws InterruptedException {
        jpgamepad1 = new JustPressed(gamepad1);
        jpgamepad2 = new JustPressed(gamepad2);


//        drive = new MecanumDrive(hardwareMap);
//        launcher = new Launcher(hardwareMap);
        slides = new Slides(hardwareMap);

        telemetry.addData("Current Slide Position", slides.getPosition());
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
//            drive.setPowersByGamepadRobotCentric(jpgamepad1.left_stick_x(), jpgamepad1.left_stick_y(), jpgamepad1.right_stick_x(), x -> (x * .75));
            // TODO: Get better multiplier Sahas; and change apply function?

//            if(jpgamepad2.a()) launcher.launch(); // TODO: launch button config
            if(jpgamepad1.dpad_up()) slides.incrementStep(Slides.IncrementDirection.UP_MAJOR);
            if(jpgamepad1.dpad_down()) slides.incrementStep(Slides.IncrementDirection.DOWN_MAJOR);
            if(jpgamepad1.dpad_right()) slides.incrementStep(Slides.IncrementDirection.UP_MINOR);
            if(jpgamepad1.dpad_left()) slides.incrementStep(Slides.IncrementDirection.DOWN_MINOR);

            telemetry.addData("Current Slide Position", slides.getPosition());
            telemetry.update();

            jpgamepad1.update();
            jpgamepad2.update();

        }


    }
}
