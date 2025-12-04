package org.firstinspires.ftc.teamcode.drive.opmode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.gobilda.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(group = "drive")
public class PinpointTesting extends LinearOpMode {
    private GoBildaPinpointDriver odo;

    @Override
    public void runOpMode() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(odo.getPosX(DistanceUnit.INCH), odo.getPosY(DistanceUnit.INCH), odo.getHeading(AngleUnit.RADIANS)));
        waitForStart();
        TrajectorySequence sama6 = drive.trajectorySequenceBuilder(new Pose2d(odo.getPosX(DistanceUnit.INCH), odo.getPosY(DistanceUnit.INCH), odo.getHeading(AngleUnit.RADIANS)))
                .strafeTo(new Vector2d(20,20))
                .build();
        while (opModeIsActive()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            if(gamepad1.a) {
                drive.followTrajectorySequence(sama6);
            }
            drive.update();
            telemetry.addData("position x", odo.getPosX(DistanceUnit.INCH));
            telemetry.addData("position y", odo.getPosY(DistanceUnit.INCH));
            telemetry.addData("velocity x", odo.getVelX(DistanceUnit.INCH));
            telemetry.addData("velocity y", odo.getVelY(DistanceUnit.INCH));

            odo.update();
            telemetry.update();
        }
    }
}