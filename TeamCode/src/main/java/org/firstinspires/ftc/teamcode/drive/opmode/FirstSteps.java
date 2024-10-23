package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@TeleOp
public class FirstSteps extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(-63.36, -67.12, Math.toRadians(27.38)))
                .splineTo(new Vector2d(-42.90, 1.60), Math.toRadians(90.00))
                .splineTo(new Vector2d(-36.89, 38.39), Math.toRadians(90.00))
                .splineTo(new Vector2d(-1.78, 42.52), Math.toRadians(90.00))
                .splineTo(new Vector2d(12.67, 39.14), Math.toRadians(-13.16))
                .build();
        waitForStart();
        if(isStopRequested()) return;
        drive.followTrajectorySequence(untitled0);
    }

}
