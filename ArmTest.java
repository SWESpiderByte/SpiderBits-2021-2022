package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ArmTest")

public class ArmTest extends LinearOpMode {

    // todo: write your code here
    private DcMotor armMotor;
    
    @Override
    public void runOpMode() {
      armMotor = hardwareMap.get(DcMotor.class, "armMotor");
      armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      armMotor.setDirection(DcMotor.Direction.FORWARD);

    // Put initialization blocks here.
      waitForStart();
        // Put run blocks here.
        while (opModeIsActive()) {
          // Put loop blocks here.
          double quarterTurn = 1120/40;
          armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          int newTarget = armMotor.getTargetPosition() + (int)quarterTurn;
          armMotor.setTargetPosition(newTarget);
          armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          armMotor.setPower(-0.6);
          
          //How do we know if the motor is done turning? 
          while (armMotor.isBusy()) {
            //do nothing? or print out telemetry info
            telemetry.addData("Status", "Quarter Turning");
            telemetry.update();
          }
          
          //Now that we are no longer busy, turn the motor off
          armMotor.setPower(0);
          //stop and reset encoder as well
          armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          
          
          telemetry.addData("Path", "Complete");
          telemetry.update();
          
        }
  }
}
