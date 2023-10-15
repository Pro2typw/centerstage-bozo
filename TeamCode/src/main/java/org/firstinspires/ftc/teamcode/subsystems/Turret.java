
// TODO: DHANUSH MAKE IT RESET IMU POSITION

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Turret {

    private BNO055IMU imu;
    Orientation angles;
    Servo turretServo;
    public static final double TURRET_HOME = 0.5;
    public static final double TURRET_MIN_RANGE = 0.0; // TODO: Set smallest valye allowed for servo pos
    public static final double TURRET_MAX_RANGE = 1.0; // TODO: Set largest valye allowed for servo pos

    double currentHeading = angles.firstAngle;

    public Turret(HardwareMap hardwareMap)
    {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu = hardwareMap.get(BNO055IMU.class, "imu"); // TODO: Replace with IMU device name
        imu.initialize(parameters);

        turretServo = hardwareMap.get(Servo.class, "servo"); // TODO: Servo device name
        turretServo.setPosition(TURRET_HOME);
    }

    public void faceBackBoard() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double currentHeading = angles.firstAngle; // Z axis is the one that changes apparently (pointing at you/up)

        double servoPosition = (currentHeading)/300.0;

        if (currentHeading >= TURRET_MIN_RANGE && currentHeading <= TURRET_MAX_RANGE) {
            turretServo.setPosition(servoPosition);
        } else {
            turretServo.setPosition(TURRET_HOME);
        }
    }
    public void resetIMU(){
        // TODO: Make a method that resets the imu position when drivers press a button
        // imu
    }
}
    //turret automatically faces backboard if between some range of angles (not when facing intake side or something)
