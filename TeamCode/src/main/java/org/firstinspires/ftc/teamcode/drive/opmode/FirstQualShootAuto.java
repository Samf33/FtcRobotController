package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class FirstQualShootAuto extends LinearOpMode {
    DcMotor smallLauncherWheels, mainLauncher, intake,mainLauncher2;
    CRServo servoLaunchRight, servoLaunchLeft;
    Boolean maxSpeed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Boolean launchOn = true;
        smallLauncherWheels = hardwareMap.dcMotor.get("slWheels");
        mainLauncher = hardwareMap.dcMotor.get("ml");
//        mainLauncher2  =hardwareMap.dcMotor.get("ml2");
        intake = hardwareMap.dcMotor.get("intake");
        servoLaunchLeft = hardwareMap.get(CRServo.class, "slLeft");
        servoLaunchRight = hardwareMap.get(CRServo.class, "slRight");
        servoLaunchLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        mainLauncher2.setDirection(DcMotorSimple.Direction.REVERSE);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();


        drive.setWeightedDrivePower(
                new Pose2d(-0.4, 0, 0)
        );
        sleep(200);
        drive.setWeightedDrivePower(
                new Pose2d(0, 0, 0)
        );

        mainLauncher.setPower(1);
        sleep(200);
        smallLauncherWheels.setPower(0.9);
        shoot();
        sleep(1000);
        mainLauncher.setPower(0);
        servoLaunchLeft.setPower(0);
        servoLaunchRight.setPower(0);
        mainLauncher.setPower(0);
        smallLauncherWheels.setPower(0);
    }
    public void shoot() {
        servoLaunchLeft.setPower(1);
        servoLaunchRight.setPower(1);
    }
}
