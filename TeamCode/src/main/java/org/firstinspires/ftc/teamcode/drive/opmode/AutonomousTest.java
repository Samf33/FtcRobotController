package org.firstinspires.ftc.teamcode.drive.opmode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.path.LineSegment;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.hardware.gobilda.*;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.ArrayList;
import java.util.List;


@Autonomous(group = "drive")
public class AutonomousTest extends LinearOpMode {

    private double stopDistance = 0.5;
    private double stopHeading = 0.1;


    private GoBildaPinpointDriver odo;

    private List<Pose2D> waypoints = new ArrayList<Pose2D>();
    private int waypointStage = 0;

    @Override
    public void runOpMode() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waypoints.add(new Pose2D(DistanceUnit.CM,0, 0, AngleUnit.RADIANS, 0));
        waypoints.add(new Pose2D(DistanceUnit.CM,100, 0, AngleUnit.RADIANS, 0));
        waypoints.add(new Pose2D(DistanceUnit.CM,100, 100, AngleUnit.RADIANS, 0));
        waypoints.add(new Pose2D(DistanceUnit.CM,0, 100, AngleUnit.RADIANS, 0));

        waitForStart();

        while (opModeIsActive()) {
            Pose2D currentPose = odo.getPosition();
            Pose2D wantedPose = waypoints.get(waypointStage);

            Pose2D change = new Pose2D(
                    DistanceUnit.CM,
                    wantedPose.getX(DistanceUnit.CM) - currentPose.getX(DistanceUnit.CM),
                    wantedPose.getY(DistanceUnit.CM) - currentPose.getY(DistanceUnit.CM),
                    AngleUnit.RADIANS,
                    wantedPose.getHeading(AngleUnit.RADIANS) - currentPose.getHeading(AngleUnit.RADIANS)
            );

            if (Math.sqrt(Math.pow(change.getX(DistanceUnit.CM), 2) + Math.pow(change.getY(DistanceUnit.CM), 2)) < stopDistance) {
                if (change.getHeading(AngleUnit.RADIANS) < stopHeading) {
                    ++waypointStage;
                    if (waypointStage >= waypoints.size()) {
                        waypointStage = 0;
                    }
                }
            }


            drive.setWeightedDrivePower(
                    new Pose2d(
                            change.getX(DistanceUnit.CM),
                            change.getY(DistanceUnit.CM),
                            change.getHeading(AngleUnit.RADIANS)
                    )
            );

            drive.update();

            telemetry.addData("position", odo.getPosition());
            telemetry.addData("target", wantedPose);


            telemetry.update();
        }
    }
}