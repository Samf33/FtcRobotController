package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.*;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@TeleOp(group = "drive")
public class TestingLimeLight extends LinearOpMode{

        public void runOpMode() throws InterruptedException {
            Limelight3A ll = hardwareMap.get(Limelight3A.class, "limelight");
            Pose2d startPos = new Pose2d(0, 0);
            double x = startPos.getX();
            double y = startPos.getY();
            ll.setPollRateHz(100);
            ll.pipelineSwitch(0);
            ll.start();
            waitForStart();
//            Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
            if (isStopRequested()) return;
            while (opModeIsActive()) {
                LLResult re = ll.getLatestResult();
                if (re != null && re.isValid()) {
                    Pose3D bp = re.getBotpose();
                        x = bp.getPosition().x;
                        y = bp.getPosition().y;
                        telemetry.addData("pos", x + ", " + y);
                        telemetry.addData("tryuingkg",re.getTx() + ", " + re.getTy());
                }
                telemetry.update();
            }
        }
    }
