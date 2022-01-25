package frc.robot;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

import org.junit.Ignore;
import java.util.function.DoubleSupplier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import frc.robot.logging.RobotLogger;

public class RobotContainerTest {

    RobotContainer _robotContainer;
    Shooter _shooter;

    @Before
    public void setup() {
        _robotContainer = new RobotContainer();
        _shooter = new Shooter();
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

    @Test
    public void testShooter() {
        _shooter.setFire(true);
        Assert.assertEquals(_shooter.getShooterState(), ShooterState.FIRE);
    }
    
}