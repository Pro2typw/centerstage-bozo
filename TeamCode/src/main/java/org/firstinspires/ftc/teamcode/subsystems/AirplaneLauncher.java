package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AirplaneLauncher {

    public Servo servo;

    public AirplaneLauncher(HardwareMap hardwareMap)
    {
        servo = hardwareMap.get(Servo.class, "servo"); // TODO: Servo device name
    }

    public void launch()
    {
        servo.setPosition(0.9); //TODO: adjust position
    }

    public void stop()
    {
        servo.setPosition(0); //TODO: adjust position
    }
}
