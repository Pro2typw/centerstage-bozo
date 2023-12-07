package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        final Pose2d StartingPose = new Pose2d(12, 72-11.2, Math.toRadians(270));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(0), 12.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(StartingPose)
                                .lineTo(new Vector2d(12, 35))
                                .addDisplacementMarker(() -> {
                                    // Place pixel on the ground
                                })
                                .lineToLinearHeading(new Pose2d( 25, 35, Math.toRadians(270)))
                                .addDisplacementMarker(() -> {
                                    // Extend slides
                                })
                                .lineToLinearHeading(new Pose2d(46, 35, Math.toRadians(0)))
                                .addDisplacementMarker(() -> {
                                    // Place pixel on backdrop
                                })
                                .strafeLeft(5)
                                .splineToLinearHeading(new Pose2d(59, 60), Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}