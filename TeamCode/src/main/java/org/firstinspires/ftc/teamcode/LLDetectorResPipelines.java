package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp
public class LLDetectorResPipelines extends CommandOpMode {

    Limelight3A limelight;
    GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        initializeLimelight();

        gamepadEx1 = new GamepadEx(gamepad1);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenActive(
                new InstantCommand(() -> limelight.pipelineSwitch(0))
        );

        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenActive(
                new InstantCommand(() -> limelight.pipelineSwitch(1))
        );

        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenActive(
                new InstantCommand(() -> limelight.pipelineSwitch(2))
        );

    }

    @Override
    public void run() {
        super.run();
        LLResult result = limelight.getLatestResult();
        if(result != null) {
            if(result.isValid()) {
                telemetry.addData("pipeline", result.getPipelineIndex());
                List<LLResultTypes.DetectorResult> detectorRes = result.getDetectorResults();
                for(LLResultTypes.DetectorResult dr : detectorRes) {
                    telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                }
            }
        }
        telemetry.update();
    }

    private void initializeLimelight() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
    }
}
