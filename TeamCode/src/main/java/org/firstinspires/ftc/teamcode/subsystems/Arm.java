package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    private Servo continuousServo1; // controls one of the intake rollers
    private Servo continuousServo2; // controls one of the intake rollers
    private Servo continuousServo3; // controls the bigger roller after intaking
    private DcMotor rollerMotor;
    private Servo liftServo1;
    private Servo liftServo2;
    private DcMotor leftSlidesMotor;
    private DcMotor rightSlidesMotor;
    private int slidePosition;
    private int newPosition;

    public enum StateArm {
        INTAKE,
        HOLD,
        OUTTAKE
    }

    public Arm(HardwareMap hardwareMap) {
        continuousServo1 = hardwareMap.servo.get("continuousServo1");
        continuousServo2 = hardwareMap.servo.get("continuousServo2");
        continuousServo3 = hardwareMap.servo.get("continuousServo3");
        rollerMotor = hardwareMap.dcMotor.get("rollerMotor");
        liftServo1 = hardwareMap.servo.get("liftServo1");
        liftServo2 = hardwareMap.servo.get("liftServo2");
        leftSlidesMotor = hardwareMap.dcMotor.get("slidesMotor1");
        rightSlidesMotor = hardwareMap.dcMotor.get("slidesMotor2");

        leftSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlidesMotor.setTargetPosition(0); //TODO: set encoder value
        leftSlidesMotor.setPower(0); //TODO: set initial power

        rightSlidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlidesMotor.setTargetPosition(0); //TODO: set encoder value
        rightSlidesMotor.setPower(0); //TODO: set initial power

    }

    public void stateArm(StateArm state) {
        switch (state) {
            case INTAKE:
                continuousServo1.setPosition(0.5); // TODO: set position
                continuousServo2.setPosition(0.5); // TODO: set position
                continuousServo3.setPosition(0.5); // TODO: set position
                rollerMotor.setPower(1.0); // TODO: set power
                break;
            case HOLD:
                continuousServo1.setPosition(0.0); // TODO: set position
                continuousServo2.setPosition(0.0); // TODO: set position
                continuousServo3.setPosition(0.5); // TODO: set position
                rollerMotor.setPower(0.0); // TODO: set power
                break;
            case OUTTAKE:
                liftServo1.setPosition(1.0); // TODO: set position
                liftServo2.setPosition(1.0); // TODO: set position
                break;

        }
    }

    public void adjustSlidePositionUp() {
        newPosition = slidePosition + 1; //TODO: Edit how much should be added to slide position per button click

        newPosition = Math.min(1, Math.max(0, newPosition)); // TODO: CHANGE THE 0 AND 1 TO THE MINIMUM AND MAXIMUM TARGET POSITION VALUES

        slidePosition = newPosition;
        leftSlidesMotor.setTargetPosition(slidePosition);
        leftSlidesMotor.setPower(0); //TODO: set constant power
        rightSlidesMotor.setTargetPosition(slidePosition);
        rightSlidesMotor.setPower(0); //TODO: set constant power
    }

    public void adjustSlidePositionDown() {
        newPosition = slidePosition - 1; //TODO: Edit how much should be subtracted to slide position per button click

        newPosition = Math.min(1, Math.max(0, newPosition)); // TODO: CHANGE THE 0 AND 1 TO THE MINIMUM AND MAXIMUM TARGET POSITION VALUES

        slidePosition = newPosition;
        leftSlidesMotor.setPower(slidePosition);
        rightSlidesMotor.setPower(slidePosition); // TODO: set constant power
        rightSlidesMotor.setTargetPosition(slidePosition);
        rightSlidesMotor.setPower(0); //TODO: set constant power
    }
}

/*
 * counter int  - when deposit goes up one - changing the height - can be changed by driver 2
 * intake
 *      - 3 servos - continuous , 1 motor (JUST FOR ROLLER)
 *      - 2 servos - noncontinuous (to lift whole arm) - 2 motor (lifting slides)
 *      -
 * drive to position
 *
 *
 */