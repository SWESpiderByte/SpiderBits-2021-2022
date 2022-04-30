package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "BlueCarouselandPark (Blocks to Java)", preselectTeleOp = "Teleop_2021")
public class BlueCarouselandPark extends LinearOpMode {

  private DcMotor armMotor;
  private DcMotor frontLeft;
  private DcMotor frontRight;
  private DcMotor backLeft;
  private DcMotor backRight;
  private DcMotor caroMotor;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
    frontRight = hardwareMap.get(DcMotor.class, "frontRight");
    backLeft = hardwareMap.get(DcMotor.class, "backLeft");
    backRight = hardwareMap.get(DcMotor.class, "backRight");
    caroMotor = hardwareMap.get(DcMotor.class, "caroMotor");

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      blueCarousel();
      while (opModeIsActive()) {
        // Put loop blocks here.
        telemetry.update();
      }
    }
  }

  /**
   * Describe this function...
   */
  private void blueCarousel() {
    // Bottom Left Corner of the Field
    // Raise arm
    // Move Backwards
    armMotor.setPower(-0.1);
    for (int count = 0; count < 1; count++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(0.5);
      sleep(1500);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    // Collect Ducks
    for (int count2 = 0; count2 < 1; count2++) {
      caroMotor.setPower(0.15);
      sleep(6000);
      caroMotor.setPower(0);
    }
    // Strafe Right
    for (int count3 = 0; count3 < 1; count3++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(-0.5);
      sleep(300);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count4 = 0; count4 < 1; count4++) {
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
      sleep(500);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count5 = 0; count5 < 1; count5++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(0.5);
      sleep(750);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count6 = 0; count6 < 1; count6++) {
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
      sleep(500);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count7 = 0; count7 < 1; count7++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(-0.5);
      sleep(2300);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count8 = 0; count8 < 1; count8++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(0.5);
      sleep(150);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
  }
}
