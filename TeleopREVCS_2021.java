package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class TeleopREVCS_2021 extends LinearOpMode {
  
  // Define a variable for our color sensor
    ColorSensor sensor_color;
    
      @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        sensor_color = hardwareMap.get(ColorSensor.class, "sensor_color");
        
        // Wait for the Play button to be pressed
        waitForStart();
 
        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            telemetry.addData("Red", sensor_color.red());
            telemetry.addData("Green", sensor_color.green());
            telemetry.addData("Blue", sensor_color.blue());
            telemetry.update();
        }
    }
}
