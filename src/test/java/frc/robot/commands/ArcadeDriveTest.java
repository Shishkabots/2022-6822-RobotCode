package frc.robot.commands;

import org.junit.Test;

import frc.robot.subsystems.DriveTrain;

import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

import java.util.function.DoubleSupplier;

import static org.junit.BeforeEach;
import static org.junit.mock;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArcadeDriveTest {
   
    private ArcadeDrive _arcadeDrive;

    @mock
    private DoubleSupplier _speed;

    @mock
    private DoubleSupplier _rotation;

    @mock
    private DriveTrain _driveTrain;

    @BeforeEach
    public void setUp() {
        when(_speed.getAsDouble()).thenReturn(2f);
        when(_rotation.getAsDouble()).thenReturn(3f);
        when(_driveTrain.arcadedrive(anyDouble(), anyDouble()).thenReturn(void.class));
        _arcadeDrive = new ArcadeDrive(_speed, _rotation, _driveTrain);
    }

    @Test
    public void testUpdateMaxMin() {
        _arcadeDrive.updateMaxMin(5, 3);
        Assert.assertEquals(_arcadeDrive.getMaxX(), 5);
        return;
    }
}