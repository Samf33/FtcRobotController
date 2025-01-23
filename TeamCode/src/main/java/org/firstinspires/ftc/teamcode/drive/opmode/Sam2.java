package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

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

    public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    public double[] getColors(){
        ColorSensor colorsense;
        test_color = hardwareMap.get(ColorSensor.class, "Sam2");
        double red = map(colorSense.red(),0, 1, 0, 255);
        double green = map(colorSense.green(),0, 1, 0, 255);
        double blue = map(colorSense.blue(),0, 1, 0, 255);
        double[] rgb = {red, green, blue};
        return rgb;
    }
    public void Claw() {
        //color logic here
    }

}
