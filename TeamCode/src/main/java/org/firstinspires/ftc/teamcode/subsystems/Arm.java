package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Arm {
    public enum ArmState {
//        INIT,
        INTAKE,
        DEPOSIT
    }
//    private enum InitToIntake {
//        START,
//        SLIDE_UP,
//        ARM_WRIST_ROTATE,
//        DONE
//    }
    private Servo arm;
    private Servo wrist;
    private Slides slides;
//    private InitToIntake initToIntakeState;
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
//        if(state == ArmState.INIT) {
//            this.state = state;
//        };

        if(state == ArmState.INTAKE) {
            setArmPosition(Constants.Arm.ARM_INTAKE_POSITION); // intake position
            setWristPosition(Constants.Arm.WRIST_INTAKE_POSITION);
        }
        else if(state == ArmState.DEPOSIT) {
            setArmPosition(0); // depo position
            setWristPosition(0);
        }
    }

//    private void doInitToIntakeStuff() {
//        if(initToIntakeState == InitToIntake.SLIDE_UP) {
//            slides.setTargetPosition(1700, Constants.Slides.MAX_POWER); // 1600 + 100 for safety
//        }
//        if(initToIntakeState == InitToIntake.ARM_WRIST_ROTATE) {
//            setArmPosition(Constants.Arm.ARM_INTAKE_POSITION);
//            setWristPosition(Constants.Arm.WRIST_INTAKE_POSITION);
//        }
//
//    }
//
//    public void update(double runtime) {
//        if(state != ArmState.INIT) return;
//
//        if(initToIntakeState == InitToIntake.START) initToIntakeState = InitToIntake.SLIDE_UP;
//        if(initToIntakeState == InitToIntake.SLIDE_UP && slides.getCurrentPosition() == 1700) {
//             initToIntakeState = InitToIntake.ARM_WRIST_ROTATE;
//        }
//        if(initToIntakeState == InitToIntake.ARM_WRIST_ROTATE) {
//            state = ArmState.INTAKE;
//        }
//        doInitToIntakeStuff();
//    }

}
