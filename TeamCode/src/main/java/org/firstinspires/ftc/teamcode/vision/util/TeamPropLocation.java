package org.firstinspires.ftc.teamcode.vision.util;

import androidx.annotation.NonNull;

public enum TeamPropLocation {
    LEFT {
        @Override
        public String toString() {
            return "left";
        }
    },
    CENTER {
        @Override
        public String toString() {
            return "center";
        }
    },
    RIGHT {
        @Override
        public String toString() {
            return "right";
        }
    },
}
