package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.game.test.HardwareTest.servoCurrent;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Hang;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.util.HtmlFormatter;
import org.firstinspires.ftc.teamcode.util.LoopRateTracker;
import org.firstinspires.ftc.teamcode.util.LynxModuleUtil;
import org.firstinspires.ftc.teamcode.util.gamepad.JustPressed;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "\uD83D\uDD35 \uD83D\uDFE5")
public class TeleOp extends LinearOpMode {

    Robot robot;
    MecanumDrive mecanumDrive;
    Hang hang;
    Launcher launcher;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        mecanumDrive = new MecanumDrive(hardwareMap);
        hang = new Hang(hardwareMap);
        launcher = new Launcher(hardwareMap);
        JustPressed justPressed1 = new JustPressed(gamepad1);
        JustPressed justPressed2 = new JustPressed(gamepad2);
        LoopRateTracker loopRateTracker = new LoopRateTracker(TimeUnit.MICROSECONDS);

        while (opModeInInit()) {
            sleep(200);
        }

        waitForStart();
        loopRateTracker.update();

        while (opModeIsActive()) {

            // reset position estimate
            if (gamepad2.left_bumper && justPressed2.right_bumper() || justPressed2.left_bumper() && gamepad2.right_bumper)
                mecanumDrive.setPoseEstimate(new Pose2d(0, -48.25, Math.toRadians(180))); // todo adjust

            if (justPressed1.x()) mecanumDrive.toggleDrivetrainCentric();

            double[] driveFields = {Math.cbrt(gamepad1.left_stick_x), -Math.cbrt(gamepad1.left_stick_y), Math.cbrt(gamepad1.right_stick_x)};
            if (gamepad1.right_bumper || gamepad1.left_stick_button || gamepad1.right_stick_button)
                driveFields = new double[]{driveFields[0] * .35, driveFields[1] * .35, driveFields[2] * .5};
            mecanumDrive.setToggleMotorPowers(driveFields[0], driveFields[1], driveFields[2], (x) -> x);
//            mecanumDrive.setToggleMotorPowers(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, (Double x) -> {
//                return Math.pow(x, 3);
//            });

            if (justPressed2.a())
                hang.stateHang(Hang.StateHang.EXTEND);
            if (justPressed2.b())
                hang.stateHang(Hang.StateHang.RETRACT);

            // Drone
            if (justPressed1.y())
                launcher.launch();

            else
                launcher.stop();

            //Code for Turret:



            // if driver 1 presses a button - make it reset imu position

            // Turret


            //Launcher:



            /*
            HtmlFormatter currentFormatting = new HtmlFormatter().bold().textColor("red");
            telemetry.addData("Pose estimate", mecanumDrive.getPoseEstimate().toString());
            telemetry.addData("Current draw summed (amps)", currentFormatting.format(getCurrent(hardwareMap)));
            try {
                telemetry.addData("Current draw chub (amps)", currentFormatting.format(Objects.requireNonNull(LynxModuleUtil.getControlHub(hardwareMap)).getCurrent(CurrentUnit.AMPS)));
            } catch (NullPointerException ignored) {}
            telemetry.addData("Loop Time (us)", loopRateTracker.update().getLoopTime());
            telemetry.update();
            */



        }
    }

    public static double getCurrent(HardwareMap hardwareMap) throws InterruptedException {
        double current = 0;
        for (DcMotorEx motor : hardwareMap.getAll(DcMotorEx.class))
            current += motor.getCurrent(CurrentUnit.AMPS);
        for (LynxModule module : hardwareMap.getAll(LynxModule.class))
            current += servoCurrent(module);
        return current;
    }
}
