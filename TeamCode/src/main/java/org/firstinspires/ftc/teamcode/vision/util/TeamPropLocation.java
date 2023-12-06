package org.firstinspires.ftc.teamcode.vision.util;

import androidx.annotation.NonNull;

public enum TeamPropLocation {
    LEFT {
        @NonNull
        @Override
        public String toString() {
            return "left";
        }
    },
    CENTER {
        @NonNull
        @Override
        public String toString() {
            return "center";
        }
    },
    RIGHT {
        @NonNull
        @Override
        public String toString() {
            return "right";
        }
    },
}
