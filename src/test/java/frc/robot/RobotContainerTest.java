package frc.robot;

import frc.robot.subsystems.DriveTrain;
import org.junit.Ignore;
import java.util.function.DoubleSupplier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class RobotContainerTest {

    RobotContainer _robotContainer;

    @Before
    public void setup() {
        _robotContainer = new RobotContainer();
    }

    @Test
    public void testInitializeDrive() {
        System.out.println("Testing initialize drive");
        _robotContainer.setDriveType("Tank Drive");
        Assert.assertEquals(_robotContainer.getDriveType(), RobotContainer.DriveType.TANK_DRIVE);
        Assert.assertEquals(1, 0, 0.01);
    }
}