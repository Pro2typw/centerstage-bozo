package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

public class Robot {
    MecanumDrive drive;
    PTO pto;
    Hang hang;
    Launcher launcher;

    public static enum ToggleRobotCentric {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }
    ToggleRobotCentric drivetrainCentric;

    public Robot(HardwareMap hardwareMap) {

        drive = new MecanumDrive(hardwareMap);
        pto = new PTO(hardwareMap);
        hang = new Hang(hardwareMap);
        launcher = new Launcher(hardwareMap);

        drivetrainCentric = ToggleRobotCentric.ROBOT_CENTRIC;
    }

    public void toggleDrivetrainCentric() {
        this.drivetrainCentric = drivetrainCentric.equals(ToggleRobotCentric.ROBOT_CENTRIC) ? ToggleRobotCentric.FIELD_CENTRIC : ToggleRobotCentric.ROBOT_CENTRIC;
    }
}
