package org.firstinspires.ftc.teamcode.drive.opmode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp(group = "drive")
public class Mac extends LinearOpMode {
    DcMotor arm;
//    Servo box = hardwareMap.servo.get("box");
//    Servo claw = hardwareMap.servo.get("claw");
    Servo box;
    Servo claw;
    DcMotor slide;
//    DcMotor slide = hardwareMap.dcMotor.get("slide");
    @Override
    public void runOpMode() throws InterruptedException {
        slide = hardwareMap.dcMotor.get("slide");
        arm = hardwareMap.dcMotor.get("arm");
        claw = hardwareMap.servo.get("claw");
        box = hardwareMap.servo.get("box");
        claw.setDirection(Servo.Direction.REVERSE);

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
            if(gamepad1.left_trigger > .1 || gamepad1.right_trigger >.1) {
                runSlide();
            } else {
                slide.setPower(0);
            }
            if(gamepad1.x || gamepad1.y) {
                runBox();
            }
            if(gamepad1.left_bumper || gamepad1.right_bumper) {
                runArm();
            } else {
                arm.setPower(0);
            }
            if(gamepad1.dpad_up|| gamepad1.dpad_right || gamepad1.dpad_left) {
                runClaw();
            }

        }
    }
    public void runSlide() {
        if(gamepad1.right_trigger >= .1) {
            slide.setPower(-gamepad1.right_trigger * .5);
        } else if(gamepad1.left_trigger >= .1) {
            slide.setPower(gamepad1.left_trigger * .5);
        } else {
            slide.setPower(0);
        }
    }
    public void runArm() {
        if(gamepad1.right_bumper) {
            arm.setPower(.8);
        } else if(gamepad1.left_bumper) {
            arm.setPower(-.5);
        } else {
            arm.setPower(0);
        }
    }
    public void runClaw() {
        if(gamepad1.dpad_left) {
            claw.setPosition(.6);
//            claw.setPosition(claw.getPosition() + .001);
        } else if(gamepad1.dpad_right) {
            claw.setPosition(.4);
        } else if(gamepad1.dpad_up) {
            claw.setPosition(.5);
        }

    }
    public void runBox() {
        if(gamepad1.x) {
            box.setPosition(box.getPosition() + .01);
        } else if(gamepad1.y) {
            box.setPosition(box.getPosition() - .01);
        }
    }
}

