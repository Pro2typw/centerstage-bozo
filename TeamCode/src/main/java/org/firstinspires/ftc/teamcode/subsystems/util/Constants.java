package org.firstinspires.ftc.teamcode.subsystems.util;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {

    @Config
    public static class Arm {
        public static final String ARM_MAP_NAME = "arm";
        public static final String WRIST_MAP_NAME = "wrist";
        public static final double ARM_INTAKE_POSITION = .14;
        public static final double WRIST_INTAKE_POSITION = .7;
        public static final double ARM_DEPOSIT_POSITION = .25; // todo: config me
        public static final double WRIST_DEPOSIT_POSITION = .9; // todo: config me

    }
    @Config
    public static class Slides {
        public static final String LEFT_SLIDE_MAP_NAME = "slide1";
        public static final String RIGHT_SLIDE_MAP_NAME = "slide2";

        public static final int MAX_HEIGHT_POSITION = 4100; //<-safety; max attempted is 4211
        public static final int MIN_HEIGHT_POSITION = 0;

        public static final double MAX_POWER = .7; // TODO: too fast?

        public static final double SLIDE_MAJOR_INCREMENTS_TICKS = 1000;
        public static final double SLIDE_MINOR_INCREMENTS_TICKS = 300;

        public static final int SLIDE_INIT_HANG_POSITION = 3300;
        public static final int SLIDE_CLIMB_HANG_POSITION = 3300;

    }
    @Config
    public static class Claw {
        public static final String RIGHT_CLAW_MAP_NAME = "rightClaw";
        public static final String LEFT_CLAW_MAP_NAME = "leftClaw";

        public static final double RIGHT_CLAW_CLOSE_POSITION = .5; // TODO
        public static final double LEFT_CLAW_CLOSE_POSITION = .47; // TODO
        public static final double RIGHT_CLAW_OPEN_POSITION = .36; // TODO
        public static final double LEFT_CLAW_OPEN_POSITION = .61; // TODO


    }
    @Config
    public static class Launcher {
        public static final String LAUNCHER_MAP_NAME = "servo";

        public static final double LAUNCHER_LAUNCH_POWER = 1; // TODO: 1 should be fine :/
    }

    public static class TurretConstants {
        public static final double TURRET_HOME = 0.5; // TODO
        public static final double TURRET_MIN_RANGE = 0.0; // TODO: Set smallest valye allowed for servo pos
        public static final double TURRET_MAX_RANGE = 1.0; // TODO: Set largest valye allowed for servo pos
    }
}
