  package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutoBLUE_2021", group="Pushbot")

public class AutoBLUE_2021 extends LinearOpMode {

    /* Declare OpMode members. */
    public HardwareMap_2021       robot   = new HardwareMap_2021();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        
        //robot.sensor_color.enableLed(true); // turn the light on for objects, turn it off if sensing color of lit up objects. 
    
    
     // Step 1: Drive forward until you find the blue line
     robot.frontLeft.setPower(0.5);
     robot.frontRight.setPower(0.5);
     robot.backLeft.setPower(0.5);
     robot.backRight.setPower(0.5);
     while ( opModeIsActive() && (robot.sensor_color.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) != 16)) {
         robot.sensor_color.enableLed(true);
         telemetry.addData("Color Number", robot.sensor_color.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
         telemetry.addData("Looking for Color", "");
         telemetry.update();
     }
     
    
   
    // Step 2: Stop. 
     robot.frontLeft.setPower(0.5);
     robot.frontRight.setPower(0.5);
     robot.backLeft.setPower(0.5);
     robot.backRight.setPower(0.5);
    
     telemetry.addData("Path", "Complete");
     telemetry.update();
     sleep(2000);
    
}
}
