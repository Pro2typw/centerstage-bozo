package org.firstinspires.ftc.teamcode.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

@TeleOp(name = "Slides Test", group = "Test")
public class SlidesPositionTest extends LinearOpMode {
    Slides slides;
    Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() throws InterruptedException {

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        slides = new Slides(hardwareMap);

        while (opModeInInit()) {
            telemetry.addData("Slides", slides.getCurrentPosition());
            telemetry.update();
        }


        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.a) slides.setTargetPosition(Constants.Slides.MAX_HEIGHT_POSITION, .2);
            if(gamepad1.b) slides.setTargetPosition(Constants.Slides.MIN_HEIGHT_POSITION, .2);

            telemetry.addData("Slides", slides.getCurrentPosition());
            telemetry.update();
        }


    }
}
