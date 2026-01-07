package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class FirstQualNonAuto extends LinearOpMode {
    DcMotor smallLauncherWheels, intake;
    DcMotorEx mainLauncher2, mainLauncher;
//    DcMotor mainLauncher2, mainLauncher;

    CRServo servoLaunchRight, servoLaunchLeft;
    Boolean maxSpeed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Boolean launchOn = true;
        int mode = 0;
        smallLauncherWheels = hardwareMap.dcMotor.get("slWheels");
        mainLauncher = hardwareMap.get(DcMotorEx.class, "ml");
        mainLauncher2  =hardwareMap.get(DcMotorEx.class, "ml2");
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
            telemetry.addData("mode", mode % 3 + "    0 is just launch1, 1 is just launch2, 2 is both");
            telemetry.addData("motor speed (in ticks)", "Launch 1: " + mainLauncher.getVelocity() + "Launch 2: " + mainLauncher2.getVelocity());
            telemetry.addData("motor speed (in RPS)", "Launch 1: " + mainLauncher.getVelocity()/28 + "Launch 2: " + mainLauncher2.getVelocity()/28);
            telemetry.addData("motor speed (in RPM)", "Launch 1: " + 60 *(mainLauncher.getVelocity()/28) + "Launch 2: " + 60  * (mainLauncher2.getVelocity()/28));

            telemetry.update();
            if (gamepad1.right_trigger >= .3) {
                in();
            } else{
                intake.setPower(0);
            }
            if (gamepad1.left_trigger >= .3) {
                shoot();
            } else {
//                mainLauncher2.setPower(0);
//                mainLauncher.setPower(0);
                servoLaunchLeft.setPower(0);
                servoLaunchRight.setPower(0);
            }
            if (gamepad1.x) {
                mode +=1;
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
//                mainLauncher2.setPower(.9);
            }
            if(!(gamepad1.right_trigger > .3)){
                smallLauncherWheels.setPower(0);
            }
            if (gamepad1.a) {
                launchOn = !launchOn;
            }
            if(launchOn) {
                if(mode % 3 == 0) {
                    mainLauncher.setPower(1);
                    mainLauncher2.setPower(0);
                } else if (mode % 3 ==1) {
                    mainLauncher2.setPower(1);
                    mainLauncher.setPower(0);
                } else {
                    mainLauncher2.setPower(1);
                    mainLauncher.setPower(1);
                }
//                mainLauncher2.setPower(1);
            } else {
                mainLauncher.setPower(0);
                mainLauncher2.setPower(0);
            }
//            if(!(gamepad1.left_trigger > .3)) {
//                servoLaunchLeft.setPower(-.3);
//                servoLaunchRight.setPower(-.3);
//            }

            // main (2) -> small
            // intake (0) -> main 1
            // small (1) -> main2 (needs to be reveersed)
            // main 2 (3) -> intake
        }
    }
    public void shoot() {
        if(!maxSpeed) {
            servoLaunchLeft.setPower(1);
            servoLaunchRight.setPower(1);
        } else {

            servoLaunchLeft.setPower(1);
            servoLaunchRight.setPower(1);
        }
//        smallLauncherWheels.setPower(.9);
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
