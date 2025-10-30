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
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.ArrayList;

@TeleOp(group = "drive")
public class ColorSensing extends LinearOpMode {
    private ColorRangeSensor test_color;

    private final float greenLenience = 4f;
    private final float purpleLenience = 15f;

    private final float greenHue = 155f;
    private final float purpleHue = 205f;

    @Override
    public void runOpMode() {
        test_color = hardwareMap.get(ColorRangeSensor.class, "test_color");
        test_color.setGain(50.0f);

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.a)
                test_color.setGain(test_color.getGain() + 0.1f);
            if(gamepad1.b)
                test_color.setGain(test_color.getGain() - 0.1f);

            NormalizedRGBA color = test_color.getNormalizedColors();

            telemetry.addData("Light Detected", test_color.getLightDetected());

            telemetry.addData("Red", "%.3f", color.red);
            telemetry.addData("Green", "%.3f", color.green);
            telemetry.addData("Blue", "%.3f", color.blue);

            telemetry.addData("Alpha", "%.3f", color.alpha);

            telemetry.addData("Distance (CM)", "%.3f", test_color.getDistance(DistanceUnit.CM));

            telemetry.addData("Gain", "%.3f", test_color.getGain());

            telemetry.addData("Hue", "%.3f", getHue(color));
            telemetry.addData("Saturation", "%.3f", getSaturation(color));
            telemetry.addData("Value", "%.3f", getValue(color));


            float hue = getHue(color);
            String ball = "no ball";

            if (hue - greenLenience < greenHue && greenHue < hue + greenLenience)
                ball = "green";
            else if (hue - purpleLenience < purpleHue && purpleHue < hue + purpleLenience)
                ball = "purple";

            telemetry.addData("Ball Color", "%s", ball);


            telemetry.update();
        }
    }

    private float getHue(NormalizedRGBA rgba)
    {
        return JavaUtil.colorToHue(rgba.toColor());
    }

    private float getSaturation(NormalizedRGBA rgba)
    {
        return JavaUtil.colorToSaturation(rgba.toColor());
    }

    private float getValue(NormalizedRGBA rgba)
    {
        return JavaUtil.colorToValue(rgba.toColor());
    }
}
