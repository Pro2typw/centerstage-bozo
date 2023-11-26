package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Launcher {

    public CRServo servo;

    public Launcher(@NonNull HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, Constants.Launcher.LAUNCHER_MAP_NAME);
    }

    public void launch() {
        servo.setPower(Constants.Launcher.LAUNCHER_LAUNCH_POWER);
    }

    public void stop() {
        servo.setPower(0);
    }
}
