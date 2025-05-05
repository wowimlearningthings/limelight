package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp
public class LLDetectorRes extends CommandOpMode {
    Limelight3A limelight;

    @Override
    public void initialize() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void run() {
        super.run();
        LLResult res = limelight.getLatestResult();
        if(res != null) {
            if(res.isValid()) {
                List<LLResultTypes.DetectorResult> detectorRes = res.getDetectorResults();
                for(LLResultTypes.DetectorResult dr : detectorRes) {
                    telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                }
            }
        }
        telemetry.update();
    }

    @Override
    public void reset() {
        super.reset();
        limelight.stop();
    }
}
