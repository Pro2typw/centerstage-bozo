package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Launcher {

    public Servo servo;

    public Launcher(HardwareMap hardwareMap)
    {
        servo = hardwareMap.get(Servo.class, "servo");
    }

    public void launch()
    {
        servo.setPosition(Constants.LauncherConstants.LAUNCHER_LAUNCH_POSITION);
    }

    public void stop()
    {
        servo.setPosition(Constants.LauncherConstants.LAUNCHER_STOP_POSITION);
    }
}
