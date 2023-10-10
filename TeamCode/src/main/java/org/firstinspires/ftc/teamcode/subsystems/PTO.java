package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.Encoder;

public class PTO {

    private DcMotor motorOne;
    private DcMotor motorTwo;

    private Encoder encoder;

    public PTO(HardwareMap hardwareMap){
        motorOne = hardwareMap.get(DcMotor.class, "PTOone");
        motorTwo = hardwareMap.get(DcMotor.class, "PTOtwo");

        motorOne.setDirection(DcMotorEx.Direction.FORWARD);
        motorTwo.setDirection(DcMotorEx.Direction.REVERSE);

        motorTwo.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motorOne.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void setIntakePower(double speed) {
        motorOne.setPower(speed);
        motorTwo.setPower(-speed);
    }

    public void setState() {

    }
    public void intakeFast(){
        double speed = .6;
        motorOne.setPower(speed);
        motorTwo.setPower(-speed);
    }

    public void intakeSlow(){
        double speed = .2;
        motorOne.setPower(speed);
        motorTwo.setPower(-speed);
    }

    public void bIntakeFast(){
        double speed = -.6;
        motorOne.setPower(speed);
        motorTwo.setPower(-speed);
    }

    public void bIntakeSlow(){
        double speed = -.2;
        motorOne.setPower(speed);
        motorTwo.setPower(-speed);
    }

    public void slidesUp(){

    }
}
