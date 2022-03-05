package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import frc.robot.Constants;

public class RangeFinderSensor {
    private AnalogPotentiometer m_rangeFinderSensor;

    /**
     * A rangefinder using ultrasonic to detect range.
     * @link{https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/analog-potentiometers-software.html}
     */
    public RangeFinderSensor() {
        m_rangeFinderSensor = new AnalogPotentiometer(Constants.ULTRASONIC_CHANNEL);

    }

    public double getReading() {
        return m_rangeFinderSensor.get();
    }

    public void closeSensor() {
        m_rangeFinderSensor.close();
    }
}