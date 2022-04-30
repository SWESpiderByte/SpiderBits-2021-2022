package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCurrentGame;

@Autonomous(name = "BlueBottomRightAuto (Blocks to Java)", preselectTeleOp = "Teleop_2021")
public class BlueBottomRightAuto extends LinearOpMode {

  private VuforiaCurrentGame vuforiaFreightFrenzy;
  private TfodCurrentGame tfodFreightFrenzy;
  private DcMotor intakeMotor;
  private DcMotor armMotor;
  private DcMotor frontLeftAsDcMotor;
  private DcMotor frontRightAsDcMotor;
  private DcMotor backLeftAsDcMotor;
  private DcMotor backRightAsDcMotor;
  private DcMotor caroMotor;

  Recognition recognition;
  boolean isDuckDetected;
  int Count;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    ElapsedTime Time;
    boolean done;
    List<Recognition> recognitions;
    int index;

    vuforiaFreightFrenzy = new VuforiaCurrentGame();
    tfodFreightFrenzy = new TfodCurrentGame();
    intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    frontLeftAsDcMotor = hardwareMap.get(DcMotor.class, "frontLeftAsDcMotor");
    frontRightAsDcMotor = hardwareMap.get(DcMotor.class, "frontRightAsDcMotor");
    backLeftAsDcMotor = hardwareMap.get(DcMotor.class, "backLeftAsDcMotor");
    backRightAsDcMotor = hardwareMap.get(DcMotor.class, "backRightAsDcMotor");
    caroMotor = hardwareMap.get(DcMotor.class, "caroMotor");

    // Sample TFOD Op Mode
    // Initialize Vuforia.
    vuforiaFreightFrenzy.initialize(
        "", // vuforiaLicenseKey
        hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
        "", // webcamCalibrationFilename
        false, // useExtendedTracking
        false, // enableCameraMonitoring
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
      Time = new ElapsedTime();
      done = false;
      Count = 0;
      isDuckDetected = false;
      // Put run blocks here.
      // Hardcode function
      deliverDuckandReturn();
      detectDuck();
    }
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        intakeMotor.setPower(0.5);
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
        }
        telemetry.update();
      }
    }
    // Deactivate TFOD.
    tfodFreightFrenzy.deactivate();

    vuforiaFreightFrenzy.close();
    tfodFreightFrenzy.close();
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
  private void deliverDuckandReturn() {
    // Bottom Left Corner of the Field
    // Raise arm
    // Move Backwards
    armMotor.setPower(-0.2);
    for (int count = 0; count < 1; count++) {
      frontLeftAsDcMotor.setPower(-0.5);
      frontRightAsDcMotor.setPower(0.5);
      backLeftAsDcMotor.setPower(-0.5);
      backRightAsDcMotor.setPower(0.5);
      sleep(1600);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    // Collect Ducks
    for (int count2 = 0; count2 < 1; count2++) {
      caroMotor.setPower(2);
      sleep(4000);
      caroMotor.setPower(0);
    }
    // Move Forwards
    for (int count3 = 0; count3 < 1; count3++) {
      frontLeftAsDcMotor.setPower(0.5);
      frontRightAsDcMotor.setPower(-0.5);
      backLeftAsDcMotor.setPower(0.5);
      backRightAsDcMotor.setPower(-0.5);
      sleep(750);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    // Turn Left
    for (int count4 = 0; count4 < 1; count4++) {
      frontLeftAsDcMotor.setPower(-0.5);
      frontRightAsDcMotor.setPower(-0.5);
      backLeftAsDcMotor.setPower(-0.5);
      backRightAsDcMotor.setPower(-0.5);
      sleep(1250);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    // Tensorflow Starts Here
  }

  /**
   * Describe this function...
   */
  private void detectDuck() {
    armMotor.setPower(-0.2);
    // Move Forwards
    for (int count5 = 0; count5 < 1; count5++) {
      frontLeftAsDcMotor.setPower(0.5);
      frontRightAsDcMotor.setPower(-0.5);
      backLeftAsDcMotor.setPower(0.5);
      backRightAsDcMotor.setPower(-0.5);
      sleep(750);
      frontLeftAsDcMotor.setPower(0);
      frontRightAsDcMotor.setPower(0);
      backLeftAsDcMotor.setPower(0);
      backRightAsDcMotor.setPower(0);
    }
    while (isDuckDetected == false) {
      // Move Forward
      for (int count6 = 0; count6 < 1; count6++) {
        frontLeftAsDcMotor.setPower(0.5);
        frontRightAsDcMotor.setPower(-0.5);
        backLeftAsDcMotor.setPower(0.5);
        backRightAsDcMotor.setPower(-0.5);
        sleep(750);
        frontLeftAsDcMotor.setPower(0);
        frontRightAsDcMotor.setPower(0);
        backLeftAsDcMotor.setPower(0);
        backRightAsDcMotor.setPower(0);
      }
      // Stop
      Count = Count + 1;
      displayInfo(Count);
      if (recognition.getLabel().equals("Duck")) {
        telemetry.addData("Duck", "Detected");
        // Move Left
        for (int count7 = 0; count7 < 1; count7++) {
          frontLeftAsDcMotor.setPower(0.5);
          frontRightAsDcMotor.setPower(0.5);
          backLeftAsDcMotor.setPower(-0.5);
          backRightAsDcMotor.setPower(-0.5);
          sleep(1500);
          frontLeftAsDcMotor.setPower(0);
          frontRightAsDcMotor.setPower(0);
          backLeftAsDcMotor.setPower(0);
          backRightAsDcMotor.setPower(0);
        }
        // Move Forwards
        for (int count8 = 0; count8 < 1; count8++) {
          frontLeftAsDcMotor.setPower(0.5);
          frontRightAsDcMotor.setPower(-0.5);
          backLeftAsDcMotor.setPower(0.5);
          backRightAsDcMotor.setPower(-0.5);
          sleep(1000);
          frontLeftAsDcMotor.setPower(0);
          frontRightAsDcMotor.setPower(0);
          backLeftAsDcMotor.setPower(0);
          backRightAsDcMotor.setPower(0);
        }
        if (Count == 1) {
          displayInfo(Count);
          for (int count9 = 0; count9 < 1; count9++) {
            armMotor.setPower(-0.5);
            sleep(250);
            armMotor.setPower(0);
          }
        }
        if (Count == 2) {
          displayInfo(Count);
          for (int count10 = 0; count10 < 1; count10++) {
            armMotor.setPower(-0.5);
            sleep(500);
            armMotor.setPower(0);
          }
        }
        if (Count == 3) {
          displayInfo(Count);
          for (int count11 = 0; count11 < 1; count11++) {
            armMotor.setPower(-0.5);
            sleep(750);
            armMotor.setPower(0);
          }
        }
        isDuckDetected = true;
      }
    }
  }
}
