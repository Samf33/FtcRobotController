package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Sam2 extends LinearOpMode {
    DcMotor verticalSlide1, verticalSlide2;
    //    Servo box = hardwareMap.servo.get("box");
//    Servo claw = hardwareMap.servo.get("claw");
    Servo horizontalSlide1, horizontalSlide2;

    @Override
    public void runOpMode() throws InterruptedException {
        verticalSlide1 = hardwareMap.dcMotor.get("verticalSlide1");
        verticalSlide2 = hardwareMap.dcMotor.get("verticalSlide2");
        horizontalSlide1 = hardwareMap.servo.get("horizontalSlide1");
        horizontalSlide2 = hardwareMap.servo.get("horizontalSlide2");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("horizontal slide 1", horizontalSlide1.getPosition());
            telemetry.addData("horizontal slide 2", horizontalSlide2.getPosition());
            telemetry.addData("vertical slide 1", verticalSlide1.getCurrentPosition());
            telemetry.addData("vertical slide 2", verticalSlide2.getCurrentPosition());
            telemetry.update();
        }
    }
}
