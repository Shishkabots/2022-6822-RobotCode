package frc.robot.commands;

//import org.junit.Test;

import frc.robot.subsystems.DriveTrain;

import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

import java.util.function.DoubleSupplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

public class ArcadeDriveTest {
   
    private ArcadeDrive _arcadeDrive;
    private DoubleSupplier _speed;
    private DoubleSupplier _rotation;
    private DriveTrain _driveTrain;

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
    public void testUpdateMaxMin() {
        // min_x and max_x were not initialized, so likely intended to be 0?
        Assert.assertEquals(_arcadeDrive.getMaxX(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinX(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMaxZ(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinZ(), 0.0d, 0.001d);

        // Case 1 - Update max_x, min_x should not change
        _arcadeDrive.updateMaxMin(5, 0);
        Assert.assertEquals(_arcadeDrive.getMaxX(), 5.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinX(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMaxZ(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinZ(), 0.0d, 0.001d);

        // Case 2 - Update min_x requires setting to negative value
        // Is this the intent?
        _arcadeDrive.updateMaxMin(-1, 0);
        Assert.assertEquals(_arcadeDrive.getMaxX(), 5.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinX(), -1.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMaxZ(), 0.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinZ(), 0.0d, 0.001d);

        // Case 3 - Update rotation_max with no change in speed
        _arcadeDrive.updateMaxMin(0, 3);
        Assert.assertEquals(_arcadeDrive.getMaxX(), 5.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinX(), -1.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMaxZ(), 3.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinZ(), 0.0d, 0.001d);

        // Case 4 - Update rotation_min with no change in speed
        _arcadeDrive.updateMaxMin(0, -2);
        Assert.assertEquals(_arcadeDrive.getMaxX(), 5.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinX(), -1.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMaxZ(), 3.0d, 0.001d);
        Assert.assertEquals(_arcadeDrive.getMinZ(), -2.0d, 0.001d);

        return;
    }
}