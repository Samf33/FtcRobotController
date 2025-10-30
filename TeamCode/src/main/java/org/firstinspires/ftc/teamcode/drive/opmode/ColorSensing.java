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
import java.util.Collections;
import java.util.List;

@TeleOp(group = "drive")
public class ColorSensing extends LinearOpMode {
    private ColorRangeSensor test_color;

    private final float greenLenience = 4f;
    private final float purpleLenience = 20f;

    private final float greenHue = 155f;
    private final float purpleHue = 210f;

    public enum Ball {
        NONE,
        GREEN,
        PURPLE
    }

    public final int maxBalls = 3;
    public List<Ball> loadedBalls = new ArrayList<Ball>();

    @Override
    public void runOpMode() {
        test_color = hardwareMap.get(ColorRangeSensor.class, "test_color");
        test_color.setGain(50.0f);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a)
                test_color.setGain(test_color.getGain() + 0.1f);
            if (gamepad1.b)
                test_color.setGain(test_color.getGain() - 0.1f);

            // TODO: Add debounce to left and right triggers
            if (gamepad1.left_trigger > 0.1)
                loadBall();

            if (gamepad1.right_trigger > 0.1)
                unloadBall();


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


            Ball ball = getBallColor();
            String ballName = ball.name();

            telemetry.addData("Ball Color", "%s", ballName);


            telemetry.update();
        }
    }

    public Ball getBallColor()
    {
        float hue = getHue(test_color.getNormalizedColors());

        if (hue - greenLenience < greenHue && greenHue < hue + greenLenience)
            return Ball.GREEN;
        else if (hue - purpleLenience < purpleHue && purpleHue < hue + purpleLenience)
            return Ball.PURPLE;
        return Ball.NONE;
    }
    public void loadBall()
    {
        Collections.rotate(loadedBalls, 1);
        loadedBalls.set(0, getBallColor());
    }

    public void unloadBall()
    {
        loadedBalls.set(2, Ball.NONE);
        Collections.rotate(loadedBalls, 1);
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
