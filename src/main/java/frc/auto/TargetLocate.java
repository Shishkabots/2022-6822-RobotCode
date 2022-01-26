package frc.auto;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.lang.Math;


public class TargetLocate extends CommandBase {

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    private double x, y, area;

    private double distanceToHub, targetSpeed;

    // Update smart dashboard with current offset values
    private void updateSmartDash() {
        SmartDashboard.putNumber("LimelightX Offset", x);
        SmartDashboard.putNumber("LimelightY Offset", y);
        SmartDashboard.putNumber("LimelightArea Offset", area);
    }

    // Return x offset from crosshair
    public double getX() {
        return x;
    }

    // Return y offset from crosshair
    public double getY() {
        return y;
    }
    // Return area offset
    public double getA(){
        return area;
    }

    /**
     * Calculate distance based on hub height, robot height, turret angle and angle offset from center. 
     * @link {https://docs.limelightvision.io/en/latest/cs_estimating_distance.html}
     * From diagram above:
     * @param angleAboveCameraInDegrees = a2
     * Constants.TURRET_ANGLE_IN_DEGREES = a1
     * Constants.HUB_HEIGHT_IN_CM = h2
     * Constants.ROBOT_HEIGHT_IN_CM = h1
     */
    public double calculateDistance(double angleAboveCameraInDegrees) {
        distanceToHub = (Constants.HUB_HEIGHT_IN_CM - Constants.ROBOT_HEIGHT_IN_CM)/(Math.tan(Constants.TURRET_ANGLE_IN_DEGREES + angleAboveCameraInDegrees));
        return distanceToHub;
    }

    /**
     * This HAS NOT factored in friction and compression yet.
     * From Neal's presentation:
     * @link {https://docs.google.com/presentation/d/1KM9b5WmxlpumDoM1dkGkE4FhkNjQsuI4mcSBXEQ7a0s/edit?usp=sharing}
     */
    public double calculateTargetSpeed(double distanceToHub) {
        targetSpeed = Math.sqrt((Constants.GRAVITY_M_PER_SEC_SQUARED * Math.pow(distanceToHub, 2)) / (2 * (distanceToHub * Math.pow(Math.cos(Constants.TURRET_ANGLE_IN_DEGREES), 2) * Math.tan(Constants.TURRET_ANGLE_IN_DEGREES) - (Constants.HUB_HEIGHT_IN_CM - Constants.ROBOT_HEIGHT_IN_CM))));
        return targetSpeed;
    }

    // Periodically run approximately every 20ms.
    public void execute() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);

        // Updating offset metrics and area
        updateSmartDash();

        /**
         * Hard coded for now, will test on limelight and update. Call to limelight angle goes here.
         * Limelight requires 5 or 10 amps fuse, which has been ordered and fit Jan 23. Necessary wire still arriving.
         */
        double angleAboveCameraInDegrees = 10;

        // Calculates distance periodically and target flywheel speed.
        SmartDashboard.putNumber("Distance to hub", calculateDistance(angleAboveCameraInDegrees));
        SmartDashboard.putNumber("Target flywheel speed", calculateTargetSpeed(distanceToHub));
    }
}
