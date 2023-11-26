package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Claw {

    public static enum ClawState {
        OPEN,
        CLOSE
    }

    private Servo rightClaw, leftClaw;
    private ClawState rightClawState, leftClawState;

    public Claw(HardwareMap hardwareMap) {
        rightClaw = hardwareMap.get(Servo.class, Constants.Claw.RIGHT_CLAW_MAP_NAME);
        leftClaw = hardwareMap.get(Servo.class, Constants.Claw.LEFT_CLAW_MAP_NAME);

        rightClawState = ClawState.OPEN;
        leftClawState = ClawState.OPEN;

        setRightClawPosition(0);
        setLeftClawPosition(0);
    }

    public void setRightClawPosition(double position) {
        rightClaw.setPosition(position);
    }
    public double getRightClawPosition() {
        return rightClaw.getPosition();
    }

    public void setLeftClawPosition(double position) {
        leftClaw.setPosition(position);
    }
    public double getLeftClawPosition() {
        return leftClaw.getPosition();
    }

    public void setRightClawState(ClawState state) {
        if(rightClawState == state) return;
        switch (state) {
            case OPEN:
                setRightClawPosition(Constants.Claw.RIGHT_CLAW_OPEN_POSITION);
                rightClawState = ClawState.OPEN;
                break;
            case CLOSE:
                setRightClawPosition(Constants.Claw.RIGHT_CLAW_CLOSE_POSITION);
                rightClawState = ClawState.CLOSE;
                break;
        }
    }

    public void setLeftClawState(ClawState state) {
        if(leftClawState == state) return;
        switch (state) {
            case OPEN:
                setLeftClawPosition(Constants.Claw.LEFT_CLAW_OPEN_POSITION);
                leftClawState = ClawState.OPEN;
                break;
            case CLOSE:
                setLeftClawPosition(Constants.Claw.LEFT_CLAW_CLOSE_POSITION);
                leftClawState = ClawState.OPEN;
                break;
        }
    }

    public void inverseRightClawState() {
        setRightClawState(rightClawState == ClawState.OPEN? ClawState.CLOSE : ClawState.OPEN);
    }

    public void inverseLeftClawState() {
        setLeftClawState(leftClawState == ClawState.OPEN? ClawState.CLOSE : ClawState.OPEN);
    }
}
