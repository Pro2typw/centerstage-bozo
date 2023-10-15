
// TODO: DHANUSH MAKE IT RESET IMU POSITION

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.subsystems.util.Constants;

public class Turret {

    private BNO055IMU imu;
    private Orientation angles;
    private Servo turretServo;

    double currentHeading;
    private final double startingHeading;

    public Turret(HardwareMap hardwareMap)
    {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu = hardwareMap.get(BNO055IMU.class, "imu"); // TODO: Replace with IMU device name
        imu.initialize(parameters);

        turretServo = hardwareMap.get(Servo.class, "servo"); // TODO: Servo device name
        turretServo.setPosition(Constants.TurretConstants.TURRET_HOME);

        currentHeading = angles.firstAngle;
        this.startingHeading = angles.firstAngle;
    }

    public void faceBackBoard() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double currentHeading = angles.firstAngle; // Z axis is the one that changes apparently (pointing at you/up)

        double servoPosition = (currentHeading)/300.0;

        if (currentHeading >= Constants.TurretConstants.TURRET_MIN_RANGE && currentHeading <= Constants.TurretConstants.TURRET_MAX_RANGE) {
            turretServo.setPosition(servoPosition);
        } else {
            turretServo.setPosition(Constants.TurretConstants.TURRET_HOME);
        }
    }
    public void resetIMU(){
        // TODO: Make a method that resets the imu position when drivers press a button


    }
}
    //turret automatically faces backboard if between some range of angles (not when facing intake side or something)
