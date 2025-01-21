package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(group = "drive")
@Config
public class Sam extends LinearOpMode {
    Pose2d startPos = new Pose2d(0,0);
    double x = startPos.getX();
    double y = startPos.getY();
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        waitForStart();
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        if (isStopRequested()) return;


        TrajectorySequence sama1 = drive.trajectorySequenceBuilder(startPos)
                .strafeTo(new Vector2d( startPos.getX()+ 10, startPos.getY()))
        .build();
        TrajectorySequence sama2 = drive.trajectorySequenceBuilder(startPos)
                .strafeTo(new Vector2d( startPos.getX()- 10, startPos.getY()))
                .build();
        TrajectorySequence sama3 = drive.trajectorySequenceBuilder(startPos)
                .strafeTo(new Vector2d( startPos.getX(), startPos.getY() + 10))
                .build();
        TrajectorySequence sama4 = drive.trajectorySequenceBuilder(startPos)
                .strafeTo(new Vector2d( startPos.getX(), startPos.getY() - 10))
                .build();
        TrajectorySequence sama5 = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(0)))
                .strafeTo(new Vector2d(21, 0))
                .lineToLinearHeading(new Pose2d(30, 35, Math.toRadians(135)))
                .waitSeconds(1)
//                .strafeTo(new Vector2d(21, 50))
                .lineToLinearHeading(new Pose2d(65, 25, Math.toRadians(90)))
//                .strafeTo(new Vector2d(65, 25))
                .strafeTo(new Vector2d(65, 8.5))


//                .turn(Math.toRadians(180))

                .build();
        TrajectorySequence sama6 = drive.trajectorySequenceBuilder(new Pose2d(0,0, Math.toRadians(0)))
                .strafeTo(new Vector2d(24, 0))
                .lineToLinearHeading(new Pose2d(30, 35, Math.toRadians(135)))
                .waitSeconds(1)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d( 25, -50))
                .build();
        while(!isStopRequested()) {
            telemetry.addData("s", drive.getPoseEstimate().getX() + "," + drive.getPoseEstimate().getY());
            if (gamepad1.a) {
                telemetry.addData("sama1 running", "x is going up ");
                drive.followTrajectorySequence(sama1);
                x += 10;
                startPos = new Pose2d(x, y);
            } else if (gamepad1.b) {
                telemetry.addData("sama2 running", "x is going down ");
                drive.followTrajectorySequence(sama2);
                x -= 10;
                startPos = new Pose2d(x, y);

            } else if (gamepad1.x) {
                telemetry.addData("sama3 running", "y is going up ");
                drive.followTrajectorySequence(sama3);
                y += 10;
                startPos = new Pose2d(x, y);
            } else if (gamepad1.y) {
                telemetry.addData("sama4 running", "y is going down ");
                drive.followTrajectorySequence(sama4);
                y -= 10;
                startPos = new Pose2d(x, y);
            } else if (gamepad1.right_bumper) {
                telemetry.addData("sama5 running", "no turning ");
                drive.followTrajectorySequence(sama5);
            } else if (gamepad1.left_bumper) {
                telemetry.addData("sama6 running", "yes turning ");
                drive.followTrajectorySequence(sama6);
            }
        }

//        while(!isStopRequested()) {
//            telemetry.addData("x", drive.getWheelPositions());
//        }
    }

}
