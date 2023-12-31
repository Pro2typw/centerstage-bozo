package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        final Pose2d StartingPose = new Pose2d(12+5, 72-11.2, Math.toRadians(270));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(0), 12.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(StartingPose)
                                .addDisplacementMarker(() -> {
//                                    arm.setState(Arm.ArmState.INTAKE);
                                })
                                .waitSeconds(2)
                                .lineToLinearHeading(new Pose2d(14, 35, Math.toRadians(180)))
                                .addDisplacementMarker(() -> {
                                    // Place pixel on the ground
//                                    claw.setRightClawState(Claw.ClawState.OPEN);
                                })
                                .waitSeconds(2)
                                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(180)))
                                .addDisplacementMarker(() -> {
                                    // Extend slides
//                                    arm.setState(Arm.ArmState.DEPOSIT);
//                                    slides.setTargetPosition(1000, Constants.Slides.MAX_POWER - .1);
                                })
                                .lineToLinearHeading(new Pose2d(52, 27, Math.toRadians(0)))
                                .addDisplacementMarker(() -> {
                                    // Place pixel on backdrop
//                                    claw.setLeftClawState(Claw.ClawState.OPEN);
                                })
                                .waitSeconds(1)
                                .back(4)
                                .strafeLeft(5)
                                .splineToLinearHeading(new Pose2d(59, 56), Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}