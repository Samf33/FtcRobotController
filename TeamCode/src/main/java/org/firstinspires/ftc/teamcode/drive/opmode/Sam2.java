package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Sam2 extends LinearOpMode {
    ColorSensor colorSensor;

    double horizontalPoses;
    boolean plusUp = true;


    DcMotor verticalSlide1, verticalSlide2;
    //    Servo box = hardwareMap.servo.get("box");
//    Servo claw = hardwareMap.servo.get("claw");
    Servo horizontalSlide1, horizontalSlide2, arm;

    Servo claw;

    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        verticalSlide1 = hardwareMap.dcMotor.get("verticalSlide1");
        verticalSlide2 = hardwareMap.dcMotor.get("verticalSlide2");
        horizontalSlide1 = hardwareMap.servo.get("horizontalSlide1");
        horizontalSlide2 = hardwareMap.servo.get("horizontalSlide2");
        arm = hardwareMap.servo.get("arm");
        claw = hardwareMap.servo.get("claw");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        horizontalPoses = horizontalSlide1.getPosition();
        horizontalSlide2.setPosition(horizontalPoses);
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
            telemetry.addData("Horizontal Slide 1", horizontalSlide1.getPosition());
            telemetry.addData("Horizontal Slide 2", horizontalSlide2.getPosition());
            telemetry.addData("Vertical Slide 1", verticalSlide1.getCurrentPosition());
            telemetry.addData("Vertical Slide 2", verticalSlide2.getCurrentPosition());
            telemetry.addData("Mode", plusUp ? "plus up" : "plus Down");
            telemetry.addData("Arm", arm.getPosition());
            telemetry.update();
            if(gamepad1.left_trigger > .15 || gamepad1.right_trigger > .15) {
                runVerticalSlides();
            } else {
                verticalSlide1.setPower(0);
                verticalSlide2.setPower(0);
            }
            if(gamepad1.left_bumper || gamepad1.right_bumper) {
                runHorizontalSlides();
            } else {
                if(horizontalSlide2.getPosition() != horizontalPoses) {
                    horizontalSlide2.setPosition(horizontalSlide1.getPosition());
                    horizontalPoses = horizontalSlide1.getPosition();
                }
            }
            if(gamepad1.dpad_up || gamepad1.dpad_down) {
                if(plusUp) {
                    runClawPlusUp();
                } else {
                    runClawPlusDown();
                }
            }
            if(gamepad1.dpad_left || gamepad1.dpad_right) {
                plusUp = !plusUp;
            }
            if(gamepad1.a || gamepad1.b) {
                runArm();
            }
        }
    }

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    public double[] getColors(){
        double red = map(colorSensor.red(),0, 1, 0, 255);
        double green = map(colorSensor.green(),0, 1, 0, 255);
        double blue = map(colorSensor.blue(),0, 1, 0, 255);
        double[] rgb = {red, green, blue};
        return rgb;
    }
    public void runClawPlusUp() {
        if(claw.getPosition() < .5 && gamepad1.dpad_down && !(getColors()[0] > 200)) {
            claw.setPosition((claw.getPosition() + .01));
        } if (claw.getPosition() < .5 && !(getColors()[0] > 200) && !(gamepad1.dpad_down)) {
            claw.setPosition(.2);
        }
         if (claw.getPosition() > .1 && gamepad1.dpad_up) {
             claw.setPosition(claw.getPosition() - .01);
         }
    }

    public void runClawPlusDown() {
        if(claw.getPosition() > .1 && gamepad1.dpad_down && !(getColors()[0] > 200)) {
            claw.setPosition((claw.getPosition() - .01));
        } else if (claw.getPosition() > .1 && !(getColors()[0] > 200) && !(gamepad1.dpad_down)) {
            claw.setPosition(.4);
        }
        if (claw.getPosition() < .5 && gamepad1.dpad_up) {
            claw.setPosition(claw.getPosition() - .01);
        }
    }

    public void runVerticalSlides() {
        if(gamepad1.right_trigger > .15 && verticalSlide1.getCurrentPosition() < .75)  {
            verticalSlide1.setPower(gamepad1.right_trigger * .75);
            verticalSlide2.setPower(gamepad1.right_trigger * .75);
        }
        else if(gamepad1.left_trigger > .15 && verticalSlide1.getCurrentPosition() > .1) {
            verticalSlide1.setPower(-gamepad1.right_trigger * .75);
            verticalSlide2.setPower(-gamepad1.right_trigger * .75);
        }
    }

    public void runArm() {
        if(gamepad1.a && arm.getPosition() < .75) {
            arm.setPosition(arm.getPosition() + .01);
        } else if(gamepad1.b && arm.getPosition() > .1) {
            arm.setPosition(arm.getPosition() -.01);
        }
    }

    public void runHorizontalSlides() {
        if(gamepad1.right_bumper && horizontalPoses < .75) {
            horizontalSlide1.setPosition(horizontalSlide1.getPosition() + .01);
            horizontalSlide2.setPosition(horizontalSlide2.getPosition() + .01);
            horizontalPoses += .01;
        }
        else if(gamepad1.left_bumper && horizontalPoses > .1) {
            horizontalSlide1.setPosition(horizontalSlide1.getPosition() - .01);
            horizontalSlide2.setPosition(horizontalSlide2.getPosition() - .01);
            horizontalPoses -= .01;
        }
    }

}
