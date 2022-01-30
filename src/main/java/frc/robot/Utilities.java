package frc.robot;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import java.lang.Math;

public class Utilities {

    public static double INCHES_TO_METERS = 39.37d;

    // Unit conversions
    public double computeCircumferenceFromDiameter(double diameter) {
        return (Math.PI * convertInchesToMeters(diameter));        
    }

    public double convertInchesToMeters(double value) {
        return (value / Constants.METER_TO_INCHES);
    }

    public double convertMetersToInches(double value) {
        return (value * Constants.METER_TO_INCHES);
    }

    // Encoder Utilities

    /**
     * Computes relative position from origin(known-position) for any number of motors passed in
     */
    public double getEncoderPositionInMeters(WPI_TalonFX... motors) {
        double sumOfPositions = 0.0d;
        int count = 0;

        for (WPI_TalonFX motor : motors) {
            sumOfPositions += controllerUnitsToMeters(motor.getSelectedSensorPosition(0));
            count++;
        }

        return sumOfPositions / count;
    }

    /**
     * Computes velocity of motors passed in by averaging them to get the closest value.
     */
    public double getEncoderVelocityInMetersPerSecond(WPI_TalonFX... motors) {
        double sumOfVelocities = 0.0d;
        int count = 0;

        for (WPI_TalonFX motor : motors) {
            sumOfVelocities += controllerVelocityToMetersPerSecond(motor.getSelectedSensorVelocity());
            count++;
        }

        return sumOfVelocities / count;
    }

    /**
     * Convert from controller units to meters
     */
    public double controllerUnitsToMeters(double encoderUnits) {
        final double encoderUnitsRealized = (Constants.ENCODER_UNITS_PER_REVOLUTION * Constants.GEARBOX_RATIO_OVER_ONE);
        return (encoderUnits * computeCircumferenceFromDiameter(Constants.WHEEL_DIAMETER_INCHES)) / encoderUnitsRealized;
    }

    /**
     * Obtain controller's velocity in meters per second
     */
    public double controllerVelocityToMetersPerSecond(double encoderUnits) {
        return controllerUnitsToMeters(encoderUnits) / Constants.SENSOR_TIME_IN_SECONDS;
    }
}
