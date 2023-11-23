package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "LM1 Teleop", group = "LM1 Game")
public class LM1Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx leftFront, leftRear, rightRear, rightFront;
        leftFront = hardwareMap.get(DcMotorEx.class, "flMec");
//        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear = hardwareMap.get(DcMotorEx.class, "blMec");
//        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear = hardwareMap.get(DcMotorEx.class, "brMec");
        rightFront = hardwareMap.get(DcMotorEx.class, "frMec");

        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        CRServo launch = hardwareMap.get(CRServo.class, "servo");
        boolean launchAirplane = false;

        waitForStart();
        while (opModeIsActive()) {

            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double lf = (y + x + rx) / denominator;
            double lb = (y - x + rx) / denominator;
            double rb = (y + x - rx) / denominator;
            double rf = (y - x - rx) / denominator;

            leftFront.setPower(lf * .75);
            leftRear.setPower(lb * .75);
            rightRear.setPower(rb * .75);
            rightFront.setPower(rf * .75    );
            if(gamepad2.a) {
                launchAirplane = true;
            }
            if(launchAirplane) {
                launch.setPower(1);
            }
        }
    }
}
