package frc.robot.commands;

import org.junit.Ignore;
import java.util.function.DoubleSupplier;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import frc.robot.subsystems.DriveTrain;
import frc.robot.RobotContainer;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDriveTest {
    private DriveTrain m_drivetrain;
    private DoubleSupplier m_speed;
    private DoubleSupplier m_rotation;
    private ArcadeDrive m_arcadeDrive;
    private RobotContainer _robotContainer;

    @Before
    public void setUp() {
        m_speed = mock(DoubleSupplier.class);
        m_rotation = mock(DoubleSupplier.class);
        m_drivetrain = mock(DriveTrain.class);
        when(m_speed.getAsDouble()).thenReturn(2.0d);
        when(m_rotation.getAsDouble()).thenReturn(3.0d);
        m_arcadeDrive = new ArcadeDrive(m_speed, m_rotation, m_drivetrain, 2.0d, false);
        _robotContainer = new RobotContainer();
    }

    @Test
    public void testArcadeDrive() {
        _robotContainer.setDriveType("Arcade Drive");
        Assert.assertEquals(_robotContainer.getDriveType(), RobotContainer.DriveType.ARCADE_DRIVE);
    }

    @Test
    public void testArcade() {
        _robotContainer.setDriveType("Arcade Drive");
        m_arcadeDrive.smoothStop(8);
        Assert.assertEquals(m_arcadeDrive.getCurrentSpeed(), 0.0, 0.0);
    }
} 