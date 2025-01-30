package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
public class ColorTesting extends LinearOpMode {
    ColorSensor colorSensor;
    public void runOpMode() {
        colorSensor = hardwareMap.colorSensor.get("colorSensor");
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
            telemetry.addData("Colors", getColors()[0] + ", " + getColors()[1] + ", " + getColors()[2]);
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
}
