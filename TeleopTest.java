
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@TeleOp(name= "TeleopTest", group="Linear OpMode")
public class TeleopTest extends LinearOpMode {
    //Drivetrain motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    boolean GP_LA = true;
    boolean GP_RA = true;
    boolean GP_UA = true;
    boolean GP_DA = true;
    boolean x = true;
    boolean b = true;

    boolean GP_LB = true;
    boolean GP_RB = true;
    double ARMPOWER = 0.5;
    double SERVO_OPEN_POS = 0.5;
    double SERVO_CLOSE_POS = -0.5;
    public void runOpMode() {
        //Drivetrain motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        //frontLeft.setDirection(DcMotor.Direction.REVERSE); //already reversed in Hw map
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        //backLeft.setDirection(DcMotor.Direction.REVERSE); //already reversed in Hw map
        //IMPORTANT: set all the motor powers to zero b4 running.
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(gamepad1.left_stick_y);
        frontRight.setPower(gamepad1.right_stick_y);
        backLeft.setPower(gamepad1.left_stick_y);
        backRight.setPower(gamepad1.right_stick_y);
        waitForStart();
        while (opModeIsActive()) {
            //Arm mechanism
            boolean y = gamepad2.y;
            boolean a = gamepad2.a;
            boolean Y = gamepad1.y; //move right and up diagonally
            boolean A = gamepad1.a; //move right and down diagonally
            boolean x = gamepad2.x; //move servo fwd to collect
            boolean b = gamepad2.b; //move servo backwards to collect
            GP_LA = gamepad1.dpad_left;
            GP_RA = gamepad1.dpad_right;
            GP_UA = gamepad1.dpad_up; //move left and up diagonally
            GP_DA = gamepad1.dpad_down; //move left and down diagonally
            GP_LB = gamepad2.left_bumper;
            GP_RB = gamepad2.right_bumper;
            //If you press the left arrow, the robot moves left.
            if (GP_LA) {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-1);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(-1);
                //If you press the right arrow, the robot moves right.
            } else if (GP_RA) {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(1);
                frontRight.setPower(-1);
                backLeft.setPower(-1);
                backRight.setPower(1);
            } else if (GP_UA) { //move left and up diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(0);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(0);
            } else if (GP_DA) { //move left and down diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-1);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(-1);
            } else if (Y) { //move right and up diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(1);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(1);
            } else if (A) { //move right and down diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(0);
                frontRight.setPower(-1);
                backLeft.setPower(-1);
                backRight.setPower(0);
                //Otherwise, move normally (fwd or back). We'll try moving diagonally for the February comp.
            } else {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-gamepad1.left_stick_y); //negated
                frontRight.setPower(-gamepad1.right_stick_y);
                backLeft.setPower(-gamepad1.left_stick_y); //negated
                backRight.setPower(-gamepad1.right_stick_y);
            }

            //Do they want to be able to control the speed of the motors or
            //Buttons to increment arm/launcher power to set the speed. - can also have binary/on/off
        }
    }
}
