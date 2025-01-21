package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp(group = "drive")
public class testingClaw extends LinearOpMode {
    DcMotor arm;
    //    Servo box = hardwareMap.servo.get("box");
//    Servo claw = hardwareMap.servo.get("claw");
    Servo box;
    Servo claw;
    DcMotor slide;

    public void runOpMode() throws InterruptedException {
        slide = hardwareMap.dcMotor.get("slide");
        arm = hardwareMap.dcMotor.get("arm");
        claw = hardwareMap.servo.get("claw");
        box = hardwareMap.servo.get("box");
//        claw.setDirection(Servo.Direction.REVERSE);

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
            telemetry.addData("slide", slide.getCurrentPosition());
            telemetry.addData("box", box.getPosition());
            telemetry.addData("arm", arm.getCurrentPosition());
            telemetry.addData("claw", claw.getPosition());
            telemetry.update();
            runClaw();
        }
    }
    public void runClaw() {
        if(gamepad1.dpad_down) {
            claw.setPosition(0);
        } else if(gamepad1.a) {
            claw.setPosition(0.1);
        } else if(gamepad1.b) {
            claw.setPosition(0.2);
        } else if(gamepad1.x) {
            claw.setPosition(0.3);
        } else if(gamepad1.y) {
            claw.setPosition(0.4);
        } else if(gamepad1.right_bumper) {
            claw.setPosition(0.5);
        }else if(gamepad1.left_bumper) {
            claw.setPosition(0.6);
        } else if(gamepad1.right_trigger >= 0.1) {
            claw.setPosition(0.7);
        } else if(gamepad1.left_trigger >= 0.1) {
            claw.setPosition(0.8);
        } else if(gamepad1.dpad_left) {
            claw.setPosition(0.9);
        } else if(gamepad1.dpad_up) {
            claw.setPosition(1);
        }
    }
}
