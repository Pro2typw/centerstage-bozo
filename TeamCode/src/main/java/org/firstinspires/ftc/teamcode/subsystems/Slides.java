package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;
import org.firstinspires.ftc.teamcode.subsystems.util.MultiMotor;

import java.util.Queue;

public class Slides {
    private MultiMotor slides;
    private int position, previousPosition;
//    Queue<Integer> previousPositions = new Queue<Integer>();
    public Slides(HardwareMap hardwareMap) {
        slides = new MultiMotor(hardwareMap, Constants.Slides.LEFT_SLIDE_MAP_NAME, Constants.Slides.RIGHT_SLIDE_MAP_NAME);
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        position = 0;
        previousPosition = 0;

        slides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) {
        slides.setPower(power);
    }

    public void setMode(DcMotor.RunMode mode) {
        slides.setMode(mode);
    }

    public void setTargetPosition(int position, double power) {
        previousPosition = getCurrentPosition();
        slides.setTargetPosition(position);
        setPower(power);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getCurrentPosition() {
        return slides.getCurrentPosition();
    }

    public void incrementStep(IncrementDirection direction) {
        if(direction == IncrementDirection.DOWN_MAJOR && position != Constants.Slides.MIN_HEIGHT_POSITION) position -= Constants.Slides.SLIDE_MAJOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.UP_MAJOR && position != Constants.Slides.MAX_HEIGHT_POSITION) position += Constants.Slides.SLIDE_MAJOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.DOWN_MINOR && position != Constants.Slides.MIN_HEIGHT_POSITION) position -= Constants.Slides.SLIDE_MINOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.UP_MINOR && position != Constants.Slides.MAX_HEIGHT_POSITION) position += Constants.Slides.SLIDE_MINOR_INCREMENTS_TICKS;

        if(position < Constants.Slides.MIN_HEIGHT_POSITION) position = Constants.Slides.MIN_HEIGHT_POSITION;
        if(position > Constants.Slides.MAX_HEIGHT_POSITION) position = Constants.Slides.MAX_HEIGHT_POSITION;

        setTargetPosition(position, Constants.Slides.MAX_POWER);
    }

    public void setPositionToBottom() {
        if(getCurrentPosition() == Constants.Slides.MIN_HEIGHT_POSITION) return;
        setTargetPosition(Constants.Slides.MIN_HEIGHT_POSITION, Constants.Slides.MAX_POWER);
    }

    public void setPositionToPreviousStep() {
        setTargetPosition(previousPosition, Constants.Slides.MAX_POWER);
    }

    public static enum IncrementDirection {
        UP_MAJOR,
        UP_MINOR,
        DOWN_MAJOR,
        DOWN_MINOR
    }

}
