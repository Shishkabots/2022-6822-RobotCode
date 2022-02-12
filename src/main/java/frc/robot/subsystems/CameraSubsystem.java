package frc.robot.subsystems;

import frc.robot.auto.LimelightCamera;
import frc.robot.auto.VisionConstants.camMode;
import frc.robot.auto.VisionConstants.ledMode;

public class CameraSubsystem {
    public LimelightCamera m_limelightCamera;

    public CameraSubsystem() {
        m_limelightCamera = new LimelightCamera();
    }

    public void setCamToDriverMode() {
        m_limelightCamera.setCamMode(camMode.DRIVER_CAMERA_MODE);
    }

    public void setLEDToOff() {
        m_limelightCamera.setLEDMode(ledMode.FORCE_OFF);
    }
}