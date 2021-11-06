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
        _robotContainer.initLogger();
    }

    @Test
    public void testInitializeDrive() {
        _robotContainer.testLogger();
        //Assert.assertEquals(_robotContainer.getDriveType(), "test working!");
    }
}