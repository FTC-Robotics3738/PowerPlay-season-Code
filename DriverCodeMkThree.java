package powerPlay.TeleOperation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.lang.Math;

@TeleOp(name = "DriverCodeMkThree", group = "Linear Opmode")

public class DriverCodeMkThree extends LinearOpMode {

    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx rightBackDrive = null;
    private DcMotorEx lift = null;
    private Servo intake = null;
    public double maxVelocity = 1400;
    public double a = 1;

    @Override
    public void runOpMode() {

        int liftBound;

        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "rightBackDrive");
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        intake = hardwareMap.get(Servo.class, "intake");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        lift.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            double max;

            double axial = Math.tan(a * -gamepad1.left_stick_y) / Math.tan(a);
            double lateral = Math.tan(a * gamepad1.left_stick_x) / Math.tan(a);
            double yaw = gamepad1.right_stick_x / 2;// Math.tan(10/7 * gamepad1.right_stick_x)/Math.tan(10/7);

            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            leftFrontPower *= maxVelocity;
            rightFrontPower *= maxVelocity;
            leftBackPower *= maxVelocity;
            rightBackPower *= maxVelocity;

            leftFrontDrive.setVelocity(leftFrontPower);
            rightFrontDrive.setVelocity(rightFrontPower);
            leftBackDrive.setVelocity(leftBackPower);
            rightBackDrive.setVelocity(rightBackPower);

            // end of movement

            if (gamepad1.left_trigger > gamepad1.right_trigger && gamepad1.left_trigger > 0.05) {
                liftBound = lift.getCurrentPosition() + (int) (1400 * (0.09 * Math.tan(1.5 * gamepad1.left_trigger)));
                if ((liftBound < 2200) && (liftBound >= 0)) {
                    lift.setTargetPosition(liftBound);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setVelocity(1400);
                }
            }
            if (gamepad1.right_trigger > gamepad1.left_trigger && gamepad1.right_trigger > 0.05) {
                liftBound = lift.getCurrentPosition() + (int) (-1400 * (0.09 * Math.tan(1.5 * gamepad1.right_trigger)));
                if ((liftBound < 2200) && (liftBound >= 0)) {
                    lift.setTargetPosition(liftBound);
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    lift.setVelocity(1400);
                }
            }

            // lift presets
            if (gamepad1.y) {
                lift.setTargetPosition(350);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad1.x) {
                lift.setTargetPosition(280);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad1.b) {
                lift.setTargetPosition(200);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad1.a) {
                lift.setTargetPosition(120);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }

            if (gamepad1.dpad_down) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad1.dpad_left) {
                lift.setTargetPosition(1500);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad1.dpad_right) {
                lift.setTargetPosition(900);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad1.dpad_up) {
                lift.setTargetPosition(2100);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }

            if (gamepad1.right_stick_button) {
                intake.setPosition(-0.4);
            }
            if (gamepad1.left_stick_button) {
                intake.setPosition(0.4);
            }
            // game pad two actions

            if (gamepad2.a) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad2.b) {
                lift.setTargetPosition(1500);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad2.x) {
                lift.setTargetPosition(900);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }
            if (gamepad2.y) {
                lift.setTargetPosition(2200);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1400);
            }

            if (gamepad2.dpad_down) {
                lift.setTargetPosition(120);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad2.dpad_right) {
                lift.setTargetPosition(280);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad2.dpad_left) {
                lift.setTargetPosition(200);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }
            if (gamepad2.dpad_up) {
                lift.setTargetPosition(360);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setVelocity(1000);
            }

        }
    }
}
