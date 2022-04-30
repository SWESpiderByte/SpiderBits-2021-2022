package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "JustMoveForward (Blocks to Java)", preselectTeleOp = "Teleop_2021")
public class JustMoveForward extends LinearOpMode {

  private DcMotor armMotor;
  private DcMotor frontLeftAsDcMotor;
  private DcMotor frontRightAsDcMotor;
  private DcMotor backLeftAsDcMotor;
  private DcMotor backRightAsDcMotor;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    frontLeftAsDcMotor = hardwareMap.get(DcMotor.class, "frontLeftAsDcMotor");
    frontRightAsDcMotor = hardwareMap.get(DcMotor.class, "frontRightAsDcMotor");
    backLeftAsDcMotor = hardwareMap.get(DcMotor.class, "backLeftAsDcMotor");
    backRightAsDcMotor = hardwareMap.get(DcMotor.class, "backRightAsDcMotor");

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      armMotor.setPower(-0.3);
      // Put run blocks here.
      moveForwards();
      while (opModeIsActive()) {
        // Put loop blocks here.
        telemetry.update();
      }
    }
  }

  /**
   * Describe this function...
   */
  private void moveForwards() {
    // Strafe Left
    for (int count = 0; count < 1; count++) {
      frontLeftAsDcMotor.setPower(-0.5);
      frontRightAsDcMotor.setPower(-0.5);
      backLeftAsDcMotor.setPower(0.5);
      backRightAsDcMotor.setPower(0.5);
      sleep(1000);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    Stop();
    // Move Forwards
    for (int count2 = 0; count2 < 1; count2++) {
      frontLeftAsDcMotor.setPower(0.5);
      frontRightAsDcMotor.setPower(-0.5);
      backLeftAsDcMotor.setPower(0.5);
      backRightAsDcMotor.setPower(-0.5);
      sleep(3000);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    Stop();
    for (int count3 = 0; count3 < 1; count3++) {
      armMotor.setPower(0.1);
      sleep(1000);
      armMotor.setPower(0);
    }
    Stop();
  }

  /**
   * Describe this function...
   */
  private void Stop() {
    for (int count4 = 0; count4 < 1; count4++) {
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
      sleep(500);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
  }
}
