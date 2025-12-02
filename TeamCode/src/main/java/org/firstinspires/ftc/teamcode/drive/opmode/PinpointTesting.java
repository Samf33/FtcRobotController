package org.firstinspires.ftc.teamcode.drive.opmode;
import com.qualcomm.hardware.gobilda.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(group = "drive")
public class PinpointTesting extends LinearOpMode {
    private GoBildaPinpointDriver odo;

    @Override
    public void runOpMode() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("position", odo.getPosition());
            telemetry.addData("velocity x", odo.getVelX(DistanceUnit.INCH));
            telemetry.addData("velocity y", odo.getVelY(DistanceUnit.INCH));


            telemetry.update();
        }
    }
}