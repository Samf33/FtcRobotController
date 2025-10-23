package org.firstinspires.ftc.teamcode.drive.opmode;
import android.graphics.ColorSpace;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(group = "drive")
public class ColorSensing extends LinearOpMode {
    private ColorRangeSensor test_color;

    @Override
    public void runOpMode() {
        test_color = hardwareMap.get(ColorRangeSensor.class, "test_color");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Light Detected", ((OpticalDistanceSensor) test_color).getLightDetected());

            NormalizedRGBA color = test_color.getNormalizedColors();

            //Determining the amount of red, green, and blue
            telemetry.addData("Red", "%.3f", color.red);
            telemetry.addData("Green", "%.3f", color.green);
            telemetry.addData("Blue", "%.3f", color.blue);

            //Determining HSV and alpha
            telemetry.addData("Alpha", "%.3f", color.alpha);

            telemetry.addData("Gain", "%.3f", test_color.getGain());

            telemetry.update();
        }
    }
}
