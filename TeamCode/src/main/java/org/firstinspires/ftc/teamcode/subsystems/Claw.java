package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    Servo rightClaw, leftClaw;
    public Claw(HardwareMap hardwareMap) {
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    }

    public void setRightClawPosition(double position) {
        rightClaw.setPosition(position);
    }
    public double getRightClawPosition() {
        return rightClaw.getPosition();
    }



}
