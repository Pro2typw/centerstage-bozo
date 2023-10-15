package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

import java.util.List;

public class Robot {
    MecanumDrive drive;
    PTO pto;
    Hang hang;
    AirplaneLauncher launcher;

    public static enum ToggleRobotCentric {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }
    ToggleRobotCentric drivetrainCentric;

    public Robot(HardwareMap hardwareMap) {

        drive = new MecanumDrive(hardwareMap);
        pto = new PTO(hardwareMap);
        hang = new Hang(hardwareMap);
        launcher = new AirplaneLauncher(hardwareMap);

        drivetrainCentric = ToggleRobotCentric.ROBOT_CENTRIC;
    }

    public void toggleDrivetrainCentric() {
        this.drivetrainCentric = drivetrainCentric.equals(ToggleRobotCentric.ROBOT_CENTRIC) ? ToggleRobotCentric.FIELD_CENTRIC : ToggleRobotCentric.ROBOT_CENTRIC;
    }
}
