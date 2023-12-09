package org.firstinspires.ftc.teamcode.vision.test.visionprocessor;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp(name = "Vision Test Op Mode", group = "VISION TEST")
public class VisionTestOpMode extends LinearOpMode {

    private VisionPortal portal;
    private VisionTest visionTest;
    WebcamName webcamName;

    @Override
    public void runOpMode() throws InterruptedException {
        visionTest = new VisionTest();
//        portal = new VisionPortal.Builder()
//                .setCamera(webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .setCameraResolution(new Size(960, 720))  // you can go up to 720 x 960
//                .setCamera(BuiltinCameraDirection.BACK)
//                .addProcessor(visionTest)
//                .build();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera cvCamera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        cvCamera.setPipeline(new OpenCvPipeline() {
            @Override
            public Mat processFrame(Mat input) {
                return (Mat) visionTest.processFrame(input, -1);
            }
        });
        cvCamera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                cvCamera.startStreaming(960, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        waitForStart();
//        portal.setProcessorEnabled(visionTest, true);

        while (opModeIsActive()) {
            telemetry.addData("Camera", webcamName.toString());
//            telemetry.addData("Camera State", portal.getCameraState());
//            telemetry.addData("VisionTest Processor Enabled", portal.getProcessorEnabled(visionTest));
//            telemetry.addData("Frame Rate (fps)", portal.getFps());
            // missing this helpful debug info with new interface :(
            telemetry.addData("Theoretical Max FPS", cvCamera.getCurrentPipelineMaxFps());
            telemetry.addData("Pipeline Time (ms)", cvCamera.getPipelineTimeMs());
            telemetry.addData("Overhead Time (ms)", cvCamera.getOverheadTimeMs());
            telemetry.addData("Total Frame Time (ms)", cvCamera.getTotalFrameTimeMs());
            telemetry.addData("Prop Position", visionTest.getPropPosition());
            telemetry.update();
        }
    }
}
