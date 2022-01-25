// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;


//import frc.robot.commands.*;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.Constants;
import frc.robot.logging.RobotLogger;
import frc.robot.RobotContainer;


  /**
   * Creates new shooter
   */
public class Shooter extends SubsystemBase {

    private final WPI_TalonFX m_shootMotor;
    private ShooterState shooterState = ShooterState.OFF;
    private final RobotLogger logger = RobotContainer.getLogger();

    public enum ShooterState {
        OFF, TARGETING, FIRE
    }
    /**
    * Initializes shooter motor.
    */
    public Shooter() {
        m_shootMotor = new WPI_TalonFX(Constants.SHOOTER_MOTOR); //test motor, change for eventual shooter.
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void updateState() {
        switch(shooterState) {
            case OFF:
                //turns off shooter   <-- code goes here
                logger.logInfo("Shooter powered off.");
                break;
            case TARGETING:
                logger.logInfo("Targeting sequence initiated.");

                //targets the goal, calculates necessary math to be ready to fire
                //set state to fire in here
                logger.logInfo("Targeting complete, ready to begin firing sequence.");
                break;
            case FIRE:
                logger.logInfo("Firing sequence initiated.");
                //fires ball by spinning flywheel at desired parameters  <-- code goes here
                logger.logInfo("Firing sequence terminated.");
                break;
        }
    }

    public void setFire(boolean ready) {
        if (ready) {
            shooterState = ShooterState.FIRE;
            updateState();
        }
    }

    //marked for removal upon addition of FIRE case fully - don't really need as this will go in the FIRE case anyway.
    public void setSpeed(double speed) {
        m_shootMotor.set(speed);
    }

    // Gets shooter state
    public Shooter.ShooterState getShooterState() {
        return shooterState;
    }
}