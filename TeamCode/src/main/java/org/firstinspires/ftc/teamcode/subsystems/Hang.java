package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    public enum StateHang {
        START,
        EXTEND,
        RETRACT,
        STOP
    }
    private DcMotor leftHang;
    private DcMotor rightHang;

    private Servo leftScissor;
    private Servo rightScissor;
    private StateHang state;

    boolean hangState;

    public Hang(HardwareMap hardwareMap) {
        leftHang = hardwareMap.get(DcMotorEx.class, "leftHangMotor");
        rightHang = hardwareMap.get(DcMotorEx.class, "rightHangMotor");

        leftHang.setDirection(DcMotorEx.Direction.FORWARD);
        rightHang.setDirection(DcMotorEx.Direction.REVERSE);

        leftHang.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightHang.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftHang.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightHang.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        state = StateHang.START;
    }





    public void stateHang(StateHang state) {

        if(this.state == state)
            return;

        switch (state) {
            case EXTEND:
                leftScissor.setPosition(0.5); // TODO: find servo pos
                rightScissor.setPosition(0.5); // TODO: find servo pos
                break;

            case RETRACT:
                leftHang.setPower(-0.5);
                rightHang.setPower(-0.5);
                break;

            case STOP:
                leftScissor.setPosition(.1); // TODO: find servo pos
                rightScissor.setPosition(.1); // TODO: find servo pos
                break;
        }

    }



}
