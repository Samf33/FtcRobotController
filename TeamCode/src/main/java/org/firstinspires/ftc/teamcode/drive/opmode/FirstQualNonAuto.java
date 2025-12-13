package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class FirstQualNonAuto extends LinearOpMode {
    DcMotor smallLauncherWheels, mainLauncher, intake,mainLauncher2;
    CRServo servoLaunchRight, servoLaunchLeft;
    Boolean maxSpeed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        smallLauncherWheels = hardwareMap.dcMotor.get("slWheels");
        mainLauncher = hardwareMap.dcMotor.get("ml");
        mainLauncher2  =hardwareMap.dcMotor.get("ml2");
        intake = hardwareMap.dcMotor.get("intake");
        servoLaunchLeft = hardwareMap.get(CRServo.class, "slLeft");
        servoLaunchRight = hardwareMap.get(CRServo.class, "slRight");
        servoLaunchLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        mainLauncher2.setDirection(DcMotorSimple.Direction.REVERSE);
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
            if (gamepad1.right_trigger >= .3) {
                in();
            }
            if (gamepad1.left_trigger >= .3) {
                shoot();
            }
            if (gamepad1.x) {
                maxSpeed = !maxSpeed;
            }
            if(gamepad1.dpad_right) {
                servoLaunchRight.setPower(1);
            }
            if(gamepad1.dpad_left) {
                servoLaunchLeft.setPower(1);
            }
            if(gamepad1.dpad_up) {
                mainLauncher.setPower(.9);
            }
            if(gamepad1.dpad_down){
                mainLauncher2.setPower(.9);
            }

            // main (2) -> small
            // intake (0) -> main 1
            // small (1) -> main2 (needs to be reveersed)
            // main 2 (3) -> intake
        }
    }
    public void shoot() {
        if(!maxSpeed) {
            mainLauncher.setPower(1);
            mainLauncher2.setPower(1);
            servoLaunchLeft.setPower(1);
            servoLaunchRight.setPower(1);
        } else {
            mainLauncher2.setPower(1);
            mainLauncher.setPower(1);
            servoLaunchLeft.setPower(1);
            servoLaunchRight.setPower(1);
        }
        smallLauncherWheels.setPower(.9);
    }
    public void in() {
        if(!gamepad1.x) {
            intake.setPower(gamepad1.right_trigger * .8);
        } else {
            intake.setPower(-.75);
        }
        smallLauncherWheels.setPower(.9);
    }
}
