package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;
import org.firstinspires.ftc.teamcode.subsystems.util.MultiMotor;

public class Slides {
    private MultiMotor slides;
    private int position;
    public Slides(HardwareMap hardwareMap) {
        slides = new MultiMotor(hardwareMap, "slide1", "slide2");
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        position = 0;
    }

    public void setPower(double power) {
        slides.setPower(power);
    }

    public void setMode(DcMotor.RunMode mode) {
        slides.setMode(mode);
    }

    public void toTargetPosition(int position, double power) {
        slides.setTargetPosition(position);
        slides.setPower(power);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public int getPosition() {
        return position;
    }

    public void incrementStep(IncrementDirection direction) {
        if(direction == IncrementDirection.DOWN_MAJOR && position != Constants.Slides.MIN_HEIGHT_POSITION) position -= Constants.Slides.SLIDE_MAJOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.UP_MAJOR && position != Constants.Slides.MAX_HEIGHT_POSITION) position += Constants.Slides.SLIDE_MAJOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.DOWN_MINOR && position != Constants.Slides.MIN_HEIGHT_POSITION) position -= Constants.Slides.SLIDE_MINOR_INCREMENTS_TICKS;
        if(direction == IncrementDirection.UP_MINOR && position != Constants.Slides.MAX_HEIGHT_POSITION) position += Constants.Slides.SLIDE_MINOR_INCREMENTS_TICKS;

        if(position < Constants.Slides.MIN_HEIGHT_POSITION) position = Constants.Slides.MIN_HEIGHT_POSITION;
        if(position > Constants.Slides.MAX_HEIGHT_POSITION) position = Constants.Slides.MAX_HEIGHT_POSITION;

        toTargetPosition(position, Constants.Slides.MAX_POWER);
    }

    public static enum IncrementDirection {
        UP_MAJOR,
        UP_MINOR,
        DOWN_MAJOR,
        DOWN_MINOR
    }

}
