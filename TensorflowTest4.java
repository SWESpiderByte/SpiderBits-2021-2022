package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCurrentGame;

@Autonomous(name = "TensorflowTest4 (Blocks to Java)")
public class TensorflowTest4 extends LinearOpMode {

  private VuforiaCurrentGame vuforiaFreightFrenzy;
  private TfodCurrentGame tfodFreightFrenzy;
  private DcMotor armMotor;
  private DcMotor intakeMotor;
  private DcMotor frontLeft;
  private DcMotor frontRight;
  private DcMotor backLeft;
  private DcMotor backRight;

  Recognition recognition;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    boolean isDuckDetected;
    List<Recognition> recognitions;
    int index;
    int SHLVL;

    vuforiaFreightFrenzy = new VuforiaCurrentGame();
    tfodFreightFrenzy = new TfodCurrentGame();
    armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
    frontRight = hardwareMap.get(DcMotor.class, "frontRight");
    backLeft = hardwareMap.get(DcMotor.class, "backLeft");
    backRight = hardwareMap.get(DcMotor.class, "backRight");

    // Sample TFOD Op Mode
    // Initialize Vuforia.
    vuforiaFreightFrenzy.initialize(
        "", // vuforiaLicenseKey
        hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
        "", // webcamCalibrationFilename
        false, // useExtendedTracking
        true, // enableCameraMonitoring
        VuforiaLocalizer.Parameters.CameraMonitorFeedback.NONE, // cameraMonitorFeedback
        0, // dx
        0, // dy
        0, // dz
        AxesOrder.XZY, // axesOrder
        90, // firstAngle
        90, // secondAngle
        0, // thirdAngle
        true); // useCompetitionFieldTargetLocations
    // Set min confidence threshold to 0.7
    tfodFreightFrenzy.initialize(vuforiaFreightFrenzy, (float) 0.7, true, true);
    // Initialize TFOD before waitForStart.
    // Init TFOD here so the object detection labels are visible
    // in the Camera Stream preview window on the Driver Station.
    tfodFreightFrenzy.activate();
    // Enable following block to zoom in on target.
    tfodFreightFrenzy.setZoom(2.5, 16 / 9);
    telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
    telemetry.addData(">", "Press Play to start");
    telemetry.update();
    // Wait for start command from Driver Station.
    waitForStart();
    if (opModeIsActive()) {
      isDuckDetected = false;
      armMotor.setPower(-0.2);
      intakeMotor.setPower(-0.5);
      // Put run blocks here.
      while (opModeIsActive() && isDuckDetected == false) {
        // Put loop blocks here.
        // Get a list of recognitions from TFOD.
        recognitions = tfodFreightFrenzy.getRecognitions();
        // If list is empty, inform the user. Otherwise, go
        // through list and display info for each recognition.
        if (recognitions.size() == 0) {
          telemetry.addData("TFOD", "No items detected.");
        } else {
          index = 0;
          // Iterate through list and call a function to
          // display info for each recognized object.
          for (Recognition recognition_item : recognitions) {
            recognition = recognition_item;
            // Display info.
            displayInfo(index);
            // Increment index.
            index = index + 1;
          }
          if (recognition.getLabel().equals("Duck")) {
            SHLVL = 3;
            telemetry.addData("Level", SHLVL);
            telemetry.update();
            level3();
            isDuckDetected = true;
          } else {
            StrafeLeftandStop();
            if (recognition.getLabel().equals("Duck")) {
              SHLVL = 2;
              telemetry.addData("Level", SHLVL);
              telemetry.update();
              level2();
              isDuckDetected = true;
            } else {
              StrafeLeftandStop();
              if (recognition.getLabel().equals("Duck")) {
                SHLVL = 1;
                telemetry.addData("Level", SHLVL);
                telemetry.update();
                level1();
                isDuckDetected = true;
              } else {
                Stop();
                isDuckDetected = true;
              }
            }
          }
        }
      }
    }
    // Deactivate TFOD.
    tfodFreightFrenzy.deactivate();

    vuforiaFreightFrenzy.close();
    tfodFreightFrenzy.close();
  }

  /**
   * Describe this function...
   */
  private void moveForwardandStop() {
    armMotor.setPower(-0.5);
    intakeMotor.setPower(-0.5);
    sleep(1000);
    for (int count = 0; count < 1; count++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(500);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    for (int count2 = 0; count2 < 1; count2++) {
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
  }

  /**
   * Display info (using telemetry) for a recognized object.
   */
  private void displayInfo(int i) {
    // Display label info.
    // Display the label and index number for the recognition.
    telemetry.addData("label " + i, recognition.getLabel());
    // Display upper corner info.
    // Display the location of the top left corner
    // of the detection boundary for the recognition
    telemetry.addData("Left, Top " + i, Double.parseDouble(JavaUtil.formatNumber(recognition.getLeft(), 0)) + ", " + Double.parseDouble(JavaUtil.formatNumber(recognition.getTop(), 0)));
    // Display lower corner info.
    // Display the location of the bottom right corner
    // of the detection boundary for the recognition
    telemetry.addData("Right, Bottom " + i, Double.parseDouble(JavaUtil.formatNumber(recognition.getRight(), 0)) + ", " + Double.parseDouble(JavaUtil.formatNumber(recognition.getBottom(), 0)));
  }

  /**
   * Describe this function...
   */
  private void StrafeLeftandStop() {
    // Move Left
    for (int count3 = 0; count3 < 1; count3++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(0.5);
      sleep(750);
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
  }

  /**
   * Describe this function...
   */
  private void Stop() {
    for (int count5 = 0; count5 < 1; count5++) {
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
  }

  /**
   * Describe this function...
   */
  private void level1() {
    // Turn left
    for (int count6 = 0; count6 < 1; count6++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(0.5);
      backRight.setPower(0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count7 = 0; count7 < 1; count7++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(1750);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Turn Right
    for (int count8 = 0; count8 < 1; count8++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(-0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count9 = 0; count9 < 1; count9++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(750);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Deposit Freight
    for (int count10 = 0; count10 < 1; count10++) {
      armMotor.setPower(0.3);
      sleep(150);
      armMotor.setPower(0);
    }
    for (int count11 = 0; count11 < 1; count11++) {
      intakeMotor.setPower(0.5);
      sleep(2000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
  }

  /**
   * Describe this function...
   */
  private void level2() {
    // Turn left
    for (int count12 = 0; count12 < 1; count12++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(0.5);
      backRight.setPower(0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count13 = 0; count13 < 1; count13++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(2000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Turn Right
    for (int count14 = 0; count14 < 1; count14++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(-0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count15 = 0; count15 < 1; count15++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(750);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Deposit Freight
    for (int count16 = 0; count16 < 1; count16++) {
      armMotor.setPower(0.3);
      sleep(150);
      armMotor.setPower(0);
    }
    for (int count17 = 0; count17 < 1; count17++) {
      intakeMotor.setPower(0.5);
      sleep(2000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
  }

  /**
   * Describe this function...
   */
  private void level3() {
    // Turn left
    for (int count18 = 0; count18 < 1; count18++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(0.5);
      backLeft.setPower(0.5);
      backRight.setPower(0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count19 = 0; count19 < 1; count19++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(2250);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Turn Right
    for (int count20 = 0; count20 < 1; count20++) {
      frontLeft.setPower(-0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(-0.5);
      backRight.setPower(-0.5);
      sleep(1000);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Move Forward
    for (int count21 = 0; count21 < 1; count21++) {
      frontLeft.setPower(0.5);
      frontRight.setPower(-0.5);
      backLeft.setPower(0.5);
      backRight.setPower(-0.5);
      sleep(750);
      frontLeft.setPower(0);
      frontRight.setPower(0);
      backLeft.setPower(0);
      backRight.setPower(0);
    }
    Stop();
    // Deposit Freight
    for (int count22 = 0; count22 < 1; count22++) {
      armMotor.setPower(-0.3);
      sleep(150);
    }
    for (int count23 = 0; count23 < 1; count23++) {
      intakeMotor.setPower(1);
      sleep(2000);
      intakeMotor.setPower(0);
    }
  }
}
