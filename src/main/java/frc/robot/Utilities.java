package frc.robot;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import java.lang.Math;

public class Utilities {

    public static double INCHES_TO_METERS = 39.37d;

    // Unit conversions
    public static double computeCircumferenceFromDiameter(double diameter) {
        return (Math.PI * convertInchesToMeters(diameter));        
    }

    public static double convertInchesToMeters(double value) {
        return (value / Constants.METER_TO_INCHES);
    }

    public static double convertMetersToInches(double value) {
        return (value * Constants.METER_TO_INCHES);
    }

    // Encoder Utilities

    /**
     * Computes relative position from origin(known-position) for any number of motors passed in
     */
    public static double getEncoderPositionInMeters(WPI_TalonFX... motors) {
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
    public static double getEncoderVelocityInMetersPerSecond(WPI_TalonFX... motors) {
        double sumOfVelocities = 0.0d;
        int count = 0;

        for (WPI_TalonFX motor : motors) {
            sumOfVelocities += controllerVelocityToMetersPerSecond(motor.getSelectedSensorVelocity());
            count++;
        }

        return sumOfVelocities / count;
    }

    /**
     * Sets motor encoder back to 0 for all Talon FXes passed in.
     * @param motors
     */
    public static void setMotorEncoderToZero(WPI_TalonFX... motors) {
        for (WPI_TalonFX motor : motors) {
            motor.setSelectedSensorPosition(0);
        }
    }

    /**
     * Convert from controller units to meters
     */
    public static double controllerUnitsToMeters(double encoderUnits) {
        final double encoderUnitsRealized = (Constants.ENCODER_UNITS_PER_REVOLUTION * Constants.GEARBOX_RATIO_OVER_ONE);
        return (encoderUnits * computeCircumferenceFromDiameter(Constants.WHEEL_DIAMETER_INCHES)) / encoderUnitsRealized;
    }

    /**
     * Obtain controller's velocity in meters per second
     */
    public static double controllerVelocityToMetersPerSecond(double encoderUnits) {
        return controllerUnitsToMeters(encoderUnits) / Constants.SENSOR_TIME_IN_SECONDS;
    }
}
