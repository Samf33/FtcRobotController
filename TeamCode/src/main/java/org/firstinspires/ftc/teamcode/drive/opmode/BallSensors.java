package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class BallSensors extends LinearOpMode {

    final double SEARCH_SPEED = 0.2;
    final double FOUND_ROTATE_SPEED = 0.4;
    final double FOUND_MOVE_SPEED = 0.8;

    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Go to 0th pipeline

        while (!isStopRequested()) {
            LLResult ball = limelight.getLatestResult();

            if (ball != null && ball.isValid()) { //WE FOUND A BALL
                ballLoop(ball.getTx());
                telemetry.addData("Status", "Going to ball");
            } else { // NO BALL
                noBallLoop();
                telemetry.addData("Status", "Searching for ball");
            }

            telemetry.update();
        }
    }

    public void ballLoop(double targetXOffset) {
        boolean goRight = targetXOffset >= 0;
        drive.setWeightedDrivePower(
                new Pose2d(
                        FOUND_MOVE_SPEED,
                        0,
                        goRight ? FOUND_ROTATE_SPEED : -FOUND_ROTATE_SPEED
                )
        );
        drive.update();
    }

    public void noBallLoop() {
        drive.setWeightedDrivePower(
                new Pose2d(
                        0,
                        0,
                        SEARCH_SPEED
                )
        );
        drive.update();
    }
}
