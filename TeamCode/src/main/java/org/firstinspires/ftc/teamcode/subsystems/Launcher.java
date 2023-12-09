package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Launcher {

    public CRServo servo;

    public static enum LaunchState{
        LAUCHING,
        STOP
    }

    public LaunchState LauncherState;

    public Launcher(@NonNull HardwareMap hardwareMap) {
        servo = hardwareMap.get(CRServo.class, "servo");
        LauncherState = LaunchState.STOP;
    }
    public void activateLauncher(LaunchState state){
        if(state == LaunchState.STOP) servo.setPower(0);
        else servo.setPower(Constants.Launcher.LAUNCHER_LAUNCH_POWER);
    }

    public void switchState(){
        activateLauncher(LauncherState == LaunchState.STOP? LaunchState.LAUCHING: LaunchState.STOP);

    }
}
