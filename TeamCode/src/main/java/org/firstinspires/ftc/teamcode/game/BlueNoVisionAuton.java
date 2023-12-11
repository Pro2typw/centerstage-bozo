package org.firstinspires.ftc.teamcode.game;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;

@Autonomous(name = "Blue Stack", group = "LM2 Game")
public class BlueNoVisionAuton extends LinearOpMode {

    private enum States {
        STATE0,
        STATE1,
        STATE2,
        StTATE3
    }

    MecanumDrive mecanumDrive;
    States states = States.STATE1;

    double opModeInitRuntime;


    public void runOpMode() throws InterruptedException {

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slides = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        claw.setLeftClawState(Claw.ClawState.CLOSE);
        claw.setRightClawState(Claw.ClawState.CLOSE);

        Arm arm = new Arm(hardwareMap, slides);
        waitForStart();
        arm.setState(Arm.ArmState.INTAKE);

    }
}
