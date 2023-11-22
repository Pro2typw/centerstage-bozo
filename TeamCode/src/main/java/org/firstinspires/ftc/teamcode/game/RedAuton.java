package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Hang;

@Autonomous(name = "Red No Vision Auton", group = "Gay")
public class RedAuton extends LinearOpMode {

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

        double initPeriod = 0;

        while(opModeInInit()) {
            initPeriod = getRuntime();
        }
        waitForStart();
        while(opModeIsActive()) {
            if(getRuntime() - initPeriod < 2.2) {
                leftFront.setPower(-.7);
                leftRear.setPower(.7);
                rightRear.setPower(-.7);
                rightFront.setPower(.7);
            }
            else {
                leftFront.setPower(0);
                leftRear.setPower(0);
                rightRear.setPower(0);
                rightFront.setPower(0);
            }

        }

    }
}
