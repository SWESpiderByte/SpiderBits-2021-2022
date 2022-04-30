/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop_2021", group="Linear Opmode")

public class Teleop_2021 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor caroMotor = null; //carousel Motor
    private DcMotor armMotor = null; //arm Motor
    private DcMotor intakeMotor = null; //intake Motor

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        caroMotor = hardwareMap.get(DcMotor.class, "caroMotor");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        caroMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double motorPower;
            
            //variables for carousel Motor 
            boolean x = gamepad2.x; // 2
            boolean y = gamepad2.y; //2 
            boolean a = gamepad1.a;
            boolean b = gamepad1.b;
            boolean GP_LA = gamepad1.dpad_left;
            boolean GP_RA = gamepad1.dpad_right;
            boolean GP_UA = gamepad1.dpad_up; //move left and up diagonally
            boolean GP_DA = gamepad1.dpad_down; //move left and down diagonally
            boolean GP_LB = gamepad2.left_bumper; //2
            boolean GP_RB = gamepad2.right_bumper; // 2
            float GP_LT = gamepad2.left_trigger;//2
            float GP_RT = gamepad2.right_trigger;//2
            

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            
            
            double drive = -gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
            motorPower = 0.6;
            

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            /*
            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);
            backLeft.setPower(leftPower);
            backRight.setPower(rightPower);
            */
            // Show the elapsed game time and wheel power.
            
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
            

             //If you press the left arrow, the robot moves left.
            if (GP_LA) {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(1);
                frontRight.setPower(1);
                backLeft.setPower(-1);
                backRight.setPower(-1);
                //If you press the right arrow, the robot moves right.
            } else if (GP_RA) {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-1);
                frontRight.setPower(-1);
                backLeft.setPower(1);
                backRight.setPower(1);
            } else if (GP_UA) { //move left and up diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(0);
                frontRight.setPower(1);
                backLeft.setPower(-1);
                backRight.setPower(0);
            } else if (GP_DA) { //move left and down diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(1);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(-1);
            } else if (a) { //move right and up diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-1);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(1);
            } else if (b) { //move right and down diagonally
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(0);
                frontRight.setPower(-1);
                backLeft.setPower(1);
                backRight.setPower(0);
                //Otherwise, move normally (fwd or back). We'll try moving diagonally for the February comp.
            }else {
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontLeft.setPower(-leftPower);
                frontRight.setPower(rightPower);
                backLeft.setPower(-leftPower);
                backRight.setPower(rightPower);
            }
            
            //Arm and Intake Motors
            
            // Arm Motor 
            // If you press y, the arm raises 
            if (y) {
                armMotor.setPower(-motorPower);
            } else if (x) { // If you press x the arm lowers
                armMotor.setPower(0.3);
            } else {
                armMotor.setPower(-0.1);
            }
            
            // Intake Motor
             if (GP_LT > 0) {
                intakeMotor.setPower(-1);
            } else {
                intakeMotor.setPower(0);
            }
            
            
             if (GP_RT > 0) {
                intakeMotor.setPower(1);
            } else {
                intakeMotor.setPower(0);
            }
            
            
            //Carousel Motor
            if (GP_LB) { //turns the wheel counterclockwise
                caroMotor.setPower(1);
                caroMotor.setDirection(DcMotor.Direction.REVERSE);
            } else if (GP_RB) { //turns the wheel clockwise
                caroMotor.setPower(-1);
                //caroMotor.setDirection(DcMotor.Direction.FORWARD);
            } else {
                caroMotor.setPower(0);
            }
            
        }
    }
}
