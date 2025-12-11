package org.firstinspires.ftc.teamcode.drive.opmode;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp(group = "drive")
public class FirstQualTesting extends LinearOpMode {
    DcMotor smallLauncherWheels, mainLauncher, intake;
    CRServo servoLaunchRight, servoLaunchLeft;
    Double servoSpeeds, launchSpeed, intakeSpeed = .5;
    Boolean launchDirection, servoDirection, intakeDirection, driveMode, maxSpeed = false;
    int selected = 0;
    String[] selectedStrings = new String[] {"Launch Motor", "Launch Servos", "Intake Motor"};

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
            telemetry.addData("Servo Speeds (from servos)", "left: " + servoLaunchLeft.getPower() + ", right:" + servoLaunchRight.getPower());
            telemetry.addData("Servo speed", servoSpeeds);
            telemetry.addData("Launch Motor Speed" , launchSpeed);
            telemetry.addData("Intake speed", intakeSpeed);
            telemetry.addData("Motor Direction" ,launchDirection ? "Forward":"Reverse");
            telemetry.addData("Servo Direction", servoDirection ? "Forward":"Reverse");
            telemetry.addData("Intake Direction", intakeDirection ? "Forward":"Reverse");
            telemetry.addData("Mode", driveMode ? "Testing" : "Drive");
            telemetry.addData("Editing Speed Of ", selectedStrings[selected % 3]);
            telemetry.addLine("Press A to change mode, B to change servo direction");
            telemetry.addLine("X to change launcher direction, y to change selected component for speed change");
            telemetry.addLine("dpad down/up to change speeds, dpad left to change intake direction");
            telemetry.update();
            if(driveMode) {
                if (gamepad1.right_trigger >= .3 || gamepad1.x) {
                    in();
                }
                if (gamepad1.left_trigger >= .3) {
                    shoot();
                }
                if(gamepad1.a) {
                    driveMode = !driveMode;
                }
            } else {
                if(gamepad1.a) {
                    driveMode = !driveMode;
                }
                if(gamepad1.b) {
                    if (servoDirection) {
                       servoLaunchLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                       servoLaunchRight.setDirection(DcMotorSimple.Direction.FORWARD);
                    } else {
                        servoLaunchLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                        servoLaunchRight.setDirection(DcMotorSimple.Direction.REVERSE);
                    }
                    servoDirection = !servoDirection;
                }
                if (gamepad1.x){
                    if (launchDirection) {
                        mainLauncher.setDirection(DcMotorSimple.Direction.FORWARD);
                    } else {
                        mainLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
                    }
                    launchDirection = !launchDirection;
                }
                if (gamepad1.y) {
                    selected += 1;
                }
                if (gamepad1.dpad_down || gamepad1.dpad_up) {
                    if (selected % 3 == 0) {
                        if (gamepad1.dpad_down) {
                            launchSpeed -= .05;
                        } else if (gamepad1.dpad_up) {
                            launchSpeed += .05;
                        }
                    } else if (selected % 3 == 1) {
                        if (gamepad1.dpad_down) {
                            servoSpeeds -= .05;
                        } else if (gamepad1.dpad_up) {
                            servoSpeeds += .05;
                        }
                    } else {
                        if (gamepad1.dpad_down) {
                            intakeSpeed -= .05;
                        } else if (gamepad1.dpad_up) {
                            intakeSpeed += .05;
                        }
                    }
                }
                if(gamepad1.dpad_left) {
                    if(intakeDirection) {
                        intake.setDirection(DcMotorSimple.Direction.FORWARD);
                    } else {
                        intake.setDirection(DcMotorSimple.Direction.REVERSE);
                    }
                    intakeDirection = !intakeDirection;
                }
            }
        }
    }

    public void shoot() {
        if(!maxSpeed) {
            mainLauncher.setPower(gamepad1.left_trigger * launchSpeed);
            servoLaunchLeft.setPower(gamepad1.right_trigger * servoSpeeds);
            servoLaunchRight.setPower(gamepad1.right_trigger * servoSpeeds);
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
