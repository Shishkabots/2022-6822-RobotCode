package frc.robot;

import org.junit.Ignore;
import java.util.function.DoubleSupplier;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import frc.robot.subsystems.DriveTrain;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDriveTest {
    private final DriveTrain m_drivetrain;
    private final DoubleSupplier m_speed;
    private final DoubleSupplier m_rotation;
    RobotContainer _robotContainer;

    @Before
    public void setUp() {
        _speed = mock(DoubleSupplier.class);
        _rotation = mock(DoubleSupplier.class);
        _driveTrain = mock(DriveTrain.class);
        when(_speed.getAsDouble()).thenReturn(2.0d);
        when(_rotation.getAsDouble()).thenReturn(3.0d);
        _arcadeDrive = new ArcadeDrive(_speed, _rotation, _driveTrain, 2.0d);
    }

    @Test
    public void testArcadeDrive() {
        _robotContainer.setDriveType("Arcade Drive");
        Assert.assertEquals(_robotContainer.getDriveType(), RobotContainer.DriveType.ARCADE_DRIVE);
    }

    @Test
    public void testArcade() {
        _robotContainer.setDriveType("Arcade Drive");
        _arcadeDrive.smoothStop(8);
        Assert.assertEquals(_robotContainer.getCurrentSpeed(), 0.0);
    }
}