package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;
import org.firstinspires.ftc.teamcode.subsystems.util.MultiMotor;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

@TeleOp(name = "Slides Test", group = "Test")
public class SlidesPositionTest extends LinearOpMode {
    MultiMotor slides;

    boolean ddownjp = false;
    boolean dupjp = true;

    @Override
    public void runOpMode() throws InterruptedException {
        slides = new MultiMotor(hardwareMap, "slide1", "slide2");

        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        double power = .5;

        while (opModeInInit()) {
            telemetry.addData("Slides", slides.getCurrentPosition());
            telemetry.update();
        }


        waitForStart();

        while(opModeIsActive()) {

            if(gamepad1.a && slides.getCurrentPosition() > 0) { // down
                slides.setPower(-power);
            }
            else if(gamepad1.y && slides.getCurrentPosition() < Constants.Slides.MAX_HEIGHT_POSITION - 100) { //up
                slides.setPower(power);
            }
            else {
                slides.setPower(0);
            }



//            if(jp.dpad_up()) {
//                power += .05;
//                slides.setPower(0);
//            }
//            if(jp.dpad_down()) {
//                power -= .05;
//                slides.setPower(0);
//            }
            if(gamepad1.dpad_up && !dupjp) {
                power += .05;
                slides.setPower(0);
                dupjp = true;
            }
            if(!gamepad1.dpad_up) dupjp = false;

            if(gamepad1.dpad_down && !ddownjp) {
                power -= .05;
                slides.setPower(0);
                ddownjp = true;
            }
            if(!gamepad1.dpad_down) ddownjp = false;



            telemetry.addData("Slides", slides.getCurrentPosition());
            telemetry.addData("Power", power);
            telemetry.update();
        }


    }
}
