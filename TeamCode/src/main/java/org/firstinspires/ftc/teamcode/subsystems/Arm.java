package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Arm {
    public enum ArmState {
        INTAKE,
        DEPOSIT
    }
    private Servo arm;
    private Servo wrist;
    private Slides slides;
    private ArmState state;

//    private

    public Arm(HardwareMap hardwareMap, Slides slides) {
        arm = hardwareMap.get(Servo.class, "arm");
        wrist = hardwareMap.get(Servo.class, "wrist");
        this.slides = slides;

//        initToIntakeState = InitToIntake.START;

    }

    public void setArmPosition(double position) {
        arm.setPosition(position);
    }

    public void setWristPosition(double position) {
        wrist.setPosition(position);
    }

    public double getArmPosition() {
        return arm.getPosition();
    }
    public double getWristPosition() {
        return wrist.getPosition();
    }


    public void setState(ArmState state) {
        if(state == this.state) return;
        this.state = state;

        if(state == ArmState.INTAKE) {
            setArmPosition(Constants.Arm.ARM_INTAKE_POSITION); // intake position
            setWristPosition(Constants.Arm.WRIST_INTAKE_POSITION);
        }
        else {
            setArmPosition(Constants.Arm.ARM_DEPOSIT_POSITION); // deposit position
            setWristPosition(Constants.Arm.WRIST_DEPOSIT_POSITION);
        }
    }


}
