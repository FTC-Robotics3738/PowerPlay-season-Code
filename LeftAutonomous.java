
package powerPlay.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;

@Autonomous(name = "LHS")
public class LeftAutonomous extends LinearOpMode {

    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx rightBackDrive = null;
    private DcMotorEx lift = null;
    private Servo intake = null;

    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/model_20230206_164024.tflite";
    private static final String[] LABELS = {
            "1",
            "2",
            "3"
    };
    private static final String VUFORIA_KEY = "Acysu7n/////AAABmUlwAtOzyEt+pagmsTPTQu+ElaAKc/bRtufWfnvCPU75nlTEcUN/tC0HKv2HYXYKOPb/qn4i2rm8LUtwbf5JQFnq/mNoGahPbaeAOijdFV6RLLWXAZcndhJ+0mVJjGetcLj5vwNQXARE1cnAi8k2K9JR63kWlv3gUHjE30hlQve6ZbCmmh7qTqOpCdaJw/chCsirUnbZ7Va85m8va7v2o/kuCHnqVXzjtEQDZx9cUyqloEceUQa45Quahjop2Mlz22tmF1a1FbPsPlt4OkmZ1CvVgEt6KEMFO9CibO+Nl1gGia5iAFxi5Or8YxWMSinOXhTZ67zvIuiljeby3W73bb9pV56vlkNEkY209kLAB1/c";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 16.0 / 9.0);
        }

        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "rightBackDrive");
        lift = hardwareMap.get(DcMotorEx.class, "lift");
        intake = hardwareMap.get(Servo.class, "intake");

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

        leftFrontDrive.setTargetPositionTolerance(10);
        leftBackDrive.setTargetPositionTolerance(10);
        rightFrontDrive.setTargetPositionTolerance(10);
        rightBackDrive.setTargetPositionTolerance(10);

        boolean recognized = false;
        int finalHaul = 0;

        waitForStart();

        if (opModeIsActive()) {
            while (!recognized) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        for (Recognition recognition : updatedRecognitions) {
                            double col = (recognition.getLeft() + recognition.getRight()) / 2;
                            double row = (recognition.getTop() + recognition.getBottom()) / 2;
                            double width = Math.abs(recognition.getRight() - recognition.getLeft());
                            double height = Math.abs(recognition.getTop() - recognition.getBottom());

                            if (recognition.getLabel().equals("1")) {
                                recognized = true;
                                finalHaul = -1000;
                                break;
                            }

                            else if (recognition.getLabel().equals("2")) {
                                recognized = true;
                                finalHaul = 0;
                                break;
                            } else if (recognition.getLabel().equals("3")) {
                                recognized = true;
                                finalHaul = 1000;
                                break;
                            }
                        }
                    }
                }
            }
            // write robot actions between the } characters using the preprogramed actions;
            // resetLift();
            grab();
            moveLift(100);
            isBusy();
            straight(2680, 500);
            isBusy();
            high();
            isBusy();
            turnToRight(500);
            isBusy();
            straight(70, 250);
            isBusy();
            moveLift(1750);
            sleep(500);
            release();
            sleep(250);
            straight(-70, 250);
            isBusy();
            strafe(500, -500);
            isBusy();
            // resetLift();
            moveLift(100);
            straight(finalHaul, 500);
            isBusy();
            turnToRight(500);

        }
    }

    private void isBusy() {
        int sum;
        do {
            sum = 0;
            sum += leftBackDrive.isBusy() ? 1 : 0;
            sum += leftFrontDrive.isBusy() ? 1 : 0;
            sum += rightFrontDrive.isBusy() ? 1 : 0;
            sum += rightBackDrive.isBusy() ? 1 : 0;
            // sum += lift.isBusy() ? 1 : 0;
            // sleep(100);
        } while (sum > 0);
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }

    public void rotate(int angle) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + angle);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - angle);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + angle);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - angle);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(800);
        rightFrontDrive.setVelocity(800);
        leftBackDrive.setVelocity(800);
        rightBackDrive.setVelocity(800);
    }

    public void relax() {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition());
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition());
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition());
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition());

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(0);
        rightFrontDrive.setVelocity(0);
        leftBackDrive.setVelocity(0);
        rightBackDrive.setVelocity(0);
    }

    public void straight(int dist, int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + dist);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + dist);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + dist);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + dist);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void turn45ToRight(int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + 450);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - 450);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + 450);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - 450);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void turn45ToLeft(int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() - 450);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + 450);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - 450);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + 450);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void turnToRight(int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + 506);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - 506);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() + 506);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() - 506);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void turnToLeft(int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() - 506);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() + 506);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - 506);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + 506);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void strafe(int dist, int spd) {
        leftFrontDrive.setTargetPosition(leftFrontDrive.getCurrentPosition() + dist);
        rightFrontDrive.setTargetPosition(rightFrontDrive.getCurrentPosition() - dist);
        leftBackDrive.setTargetPosition(leftBackDrive.getCurrentPosition() - dist);
        rightBackDrive.setTargetPosition(rightBackDrive.getCurrentPosition() + dist);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontDrive.setVelocity(spd);
        rightFrontDrive.setVelocity(spd);
        leftBackDrive.setVelocity(spd);
        rightBackDrive.setVelocity(spd);
    }

    public void release() {
        intake.setPosition(-0.4);
    }

    public void grab() {
        intake.setPosition(0.4);
    }

    public void moveLift(int pos) {
        lift.setTargetPosition(pos);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setVelocity(1400);
    }

    public void fifth() {
        moveLift(360);
    }

    public void high() {
        moveLift(2100);
    }

    public void mid() {
        moveLift(1500);
    }

    public void resetLift() {
        moveLift(0);
        release();
    }

    public boolean check() {
        if (!leftFrontDrive.isBusy() && !leftBackDrive.isBusy() && !rightFrontDrive.isBusy() && !rightBackDrive.isBusy()
                && !lift.isBusy()) {
            return true;
        }
        return false;
    }

}
