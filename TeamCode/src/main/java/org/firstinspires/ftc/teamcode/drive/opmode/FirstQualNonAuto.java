package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class FirstQualNonAuto extends LinearOpMode {
    DcMotor smallLauncherWheels, mainLauncher, intake;
    CRServo servoLaunchRight, servoLaunchLeft;
    Boolean maxSpeed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        smallLauncherWheels = hardwareMap.dcMotor.get("slWheels");
        mainLauncher = hardwareMap.dcMotor.get("ml");
        intake = hardwareMap.dcMotor.get("intake");
        servoLaunchLeft = hardwareMap.get(CRServo.class, "slLeft");
        servoLaunchRight = hardwareMap.get(CRServo.class, "slRight");
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
            telemetry.addData("Servo Speeds", "left: " + servoLaunchLeft.getPower() + ", right:" + servoLaunchRight.getPower());
            telemetry.update();
            if (gamepad1.right_trigger >= .3 || gamepad1.x) {
                in();
            }
            if (gamepad1.left_trigger >= .3) {
                shoot();
            }
        }
    }
    public void shoot() {
        if(!maxSpeed) {
            mainLauncher.setPower(gamepad1.left_trigger * .75);
            servoLaunchLeft.setPower(gamepad1.right_trigger * .5);
            servoLaunchRight.setPower(gamepad1.right_trigger * .5);
        } else {
            mainLauncher.setPower(gamepad1.left_trigger);
            servoLaunchLeft.setPower(gamepad1.right_trigger * .75);
            servoLaunchRight.setPower(gamepad1.right_trigger * .75);
        }
    }
    public void in() {
        if(!gamepad1.x) {
            intake.setPower(gamepad1.right_trigger * .8);
        } else {
            intake.setPower(-.75);
        }
    }
}
