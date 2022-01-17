package frc.robot;

import frc.robot.subsystems.DriveTrain;
import org.junit.Ignore;
import java.util.function.DoubleSupplier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import frc.robot.logging.RobotLogger;

public class RobotContainerTest {

    RobotContainer _robotContainer;

    @Before
    public void setup() {
        _robotContainer = new RobotContainer();
    }

    @Test
    public void testInitializeDrive() {
        _robotContainer.setDriveType("Tank Drive");
        Assert.assertEquals(_robotContainer.getDriveType(), RobotContainer.DriveType.TANK_DRIVE);
    }

    @Test
    public void testLogger() {
        RobotLogger logger = RobotContainer.getLogger();
        logger.logInfo("Test working!");
        logger.logError("Errors working!");
    }
    
}