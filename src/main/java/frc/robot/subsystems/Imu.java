package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.logging.RobotLogger;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.math.controller.PIDController;

public class Imu extends SubsystemBase {
    /**
     * Hardware manual: https://store.ctr-electronics.com/content/user-manual/Pigeon%20IMU%20User's%20Guide.pdf
     * Kauai NavX API: https://www.kauailabs.com/public_files/navx-mxp/apidocs/java/com/kauailabs/navx/frc/AHRS.html
     */

    private AHRS m_navxAhrs;
    private final RobotLogger logger = RobotContainer.getLogger();

    /*
    For reference:
    Yaw is the direction at which the robot is facing
    It is measured in degrees, not radians
    */


    public Imu () {
        m_navxAhrs = new AHRS(SerialPort.Port.kMXP);
        initialize();
    }

    @Override
    public void periodic(){
        //SmartDashboard.putNumber(Constants.TARGET_DEGREES_KEY, m_navxAhrs.getYaw());
    }

    public void initialize() {

        // Calibrates board during robotInit.   
        m_navxAhrs.calibrate();
    }


    public float getPitch() {
        return m_navxAhrs.getPitch();
    }

    public float getRoll() {
        return m_navxAhrs.getRoll();
    }

    public float getYaw() {
        return m_navxAhrs.getYaw();
    }

    public boolean isCalibrating() {
        return m_navxAhrs.isCalibrating();
    }

    public void reset() {
        m_navxAhrs.reset();
    }
}