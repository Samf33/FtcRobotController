package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp(group = "drive")
public class Sam2 extends LinearOpMode {
//    ColorSensor colorSensor;

    double horizontalPoses;
    boolean plusUp = true, isBlue = false, isRed = false, isGreen = false;


    DcMotor verticalSlide1, verticalSlide2;
    //    Servo box = hardwareMap.servo.get("box");
//    Servo claw = hardwareMap.servo.get("claw");
    CRServo horizontalSlide1, horizontalSlide2, arm, arm1;

    Servo claw;

    @Override
    public void runOpMode() throws InterruptedException {
//        colorSensor = hardwareMap.colorSensor.get("colorSensor");
        verticalSlide1 = hardwareMap.dcMotor.get("verticalSlide1");
        verticalSlide2 = hardwareMap.dcMotor.get("verticalSlide2");
        horizontalSlide1 = hardwareMap.get(CRServo.class, "horizontalSlide1");
        horizontalSlide2 = hardwareMap.get(CRServo.class, "horizontalSlide2");
        arm = hardwareMap.get(CRServo.class, "arm");
        arm1 = hardwareMap.get(CRServo.class, "arm1");
        arm1.setDirection(DcMotorSimple.Direction.REVERSE);

        horizontalSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
//        verticalSlide1.setDirection(DcMotorSimple.Direction.REVERSE);


        claw = hardwareMap.servo.get("claw");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//        horizontalPoses = horizontalSlide1.getPosition();
//        horizontalSlide2.setPosition(horizontalPoses);
//        arm.setDirection(Servo.Direction.REVERSE);
//        arm1.setDirection(Servo.Direction.REVERSE);

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
            telemetry.addData("Horizontal Slide 1", horizontalSlide1.getPower());
            telemetry.addData("Horizontal Slide 2", horizontalSlide2.getPower());
            telemetry.addData("Vertical Slide 1", verticalSlide1.getCurrentPosition());
            telemetry.addData("Vertical Slide 2", verticalSlide2.getCurrentPosition());
            telemetry.addData("Mode", plusUp ? "plus up" : "plus Down");
            telemetry.addData("Arm", arm.getPower());
            telemetry.addData("Claw", claw.getPosition());
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
                horizontalSlide1.setPower(0);
                horizontalSlide2.setPower(0);
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
            } else {
                arm.setPower(0);
                arm1.setPower(0);
            }
//            findColor();
        }
    }
//    public void findColor() {
//        int iRed = getColors()[0];
//        int iBlue = getColors()[2];
//        int iGreen = getColors()[1];
//        isRed = false;
//        isGreen = false;
//        isBlue = false;
//        if(iRed > 400 && iBlue < 300 && iGreen < 275) {
//            isRed = true;
//        }  else if(iRed < 300 && iBlue > 400 && iGreen < 300) {
//            isBlue = true;
//        } else if(iRed > 400 && iBlue < 300 && iGreen > 400) {
//            isGreen = true;
//        }
//    }

//    public int[] getColors(){
//        colorSensor.enableLed(true);
//        int red = colorSensor.red();
//        int blue = colorSensor.blue();
//        int green = colorSensor.green();
//        int[] rgb = {red, green, blue};
//        return rgb;
//    }
    public void runClawPlusUp() {
        if(claw.getPosition() < .9 && gamepad1.dpad_down) {
            claw.setPosition((claw.getPosition() + .01));
        } if (claw.getPosition() < .9 && !(gamepad1.dpad_down)) {
            claw.setPosition(.2);
        }
         if (claw.getPosition() > .1 && gamepad1.dpad_up) {
             claw.setPosition(claw.getPosition() - .01);
         }
    }

    public void runClawPlusDown() {
        if(claw.getPosition() > .1 && gamepad1.dpad_down) {
            claw.setPosition((claw.getPosition() - .01));
        } else if (claw.getPosition() > .1 && !(gamepad1.dpad_down)) {
            claw.setPosition(.4);
        }
        if (claw.getPosition() < .9 && gamepad1.dpad_up) {
            claw.setPosition(claw.getPosition() - .01);
        }
    }

    public void runVerticalSlides() {
        if(gamepad1.right_trigger > .15 && verticalSlide1.getCurrentPosition() < .75)  {
            verticalSlide1.setPower(gamepad1.right_trigger * .5);
            verticalSlide2.setPower(gamepad1.right_trigger * .5);
        }
        else if(gamepad1.left_trigger > .15 && verticalSlide1.getCurrentPosition() > .1) {
            verticalSlide1.setPower(-gamepad1.right_trigger * .75);
            verticalSlide2.setPower(-gamepad1.right_trigger * .75);
        }
    }

    public void runArm() {
        if(gamepad1.a) {
            arm.setPower(.75);
            arm1.setPower(.75);
        } else if(gamepad1.b) {
            arm.setPower(-.75);
            arm1.setPower(-.75);
        }
    }

    public void runHorizontalSlides() {
        if(gamepad1.right_bumper) {
            horizontalSlide1.setPower(.75);
            horizontalSlide2.setPower(.75);
//            horizontalSlide1.setPosition(.5);
//            horizontalSlide2.setPosition(.5);
        }
        else if(gamepad1.left_bumper) {
            horizontalSlide1.setPower(-.25);
            horizontalSlide2.setPower(-.25);
            horizontalPoses -= .001;
        }
    }

}
