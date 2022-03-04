  // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.logging.RobotLogger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Imu;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ColorSensor;
import frc.robot.auto.BallTracker;

/** An example command that uses an example subsystem. */
public class AutoCommand extends CommandBase {

    private Imu m_imu;
    private DriveTrain m_driveTrain;
    private BallTracker m_ballTracker;
    private BallCoordinates mostConfidentBallCoordinates;
    private ColorSensor m_colorSensor;

    private double kP = 0.3, kI = 0.3, kD = 1;
    private double integral, previous_error, error = 0;
    private int setpoint = Constants.CAMERA_WIDTH_IN_PIXELS_OVER_TWO;
    private double rcw;

    private AutonomousState m_autonomousState;

    private enum AutonomousState {
      SCORE_PRELOADED_BALL, GO_TO_BALL, SCORE_BALL
    }

  private final RobotLogger logger = RobotContainer.getLogger();

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param  drivetrain The drivetrain used by this command.
   */
  public AutoCommand(Imu imu, DriveTrain driveTrain, BallTracker ballTracker, ColorSensor colorSensor) {
    m_imu = imu;
    m_driveTrain = driveTrain;
    m_ballTracker = ballTracker;
    m_colorSensor = colorSensor;
    addRequirements(m_imu, m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    logger.logInfo("Autonomous Command initialized!");
    m_imu.reset();
    m_autonomousState = AutonomousState.GO_TO_BALL;
   }

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
    //checkAutonomousState(); will be uncommented when the full method is done.
      chooseMostConfidentBall();
      PIDTurningControl();
      turnToBall();
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    logger.logInfo("Arcadedrive ended, interrupted = " + interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  
  } 


  public void checkAutonomousState() {
    switch (m_autonomousState) {
      case SCORE_PRELOADED_BALL:
        //run motor to drop ball.
        break;
      case GO_TO_BALL:
        chooseMostConfidentBall();
        PIDTurningControl();
        turnToBall();
        // add going to ball methods
        if (ballPickedUp()) {
          m_autonomousState = AutonomousState.SCORE_BALL;
        }
        break;
      case SCORE_BALL:
        if (ballPickedUp()) {
          //if (m_imu.getYaw() > -2 || m_imu.getYaw() < 2)
          //turnBackToHub(); //implement pid control here?
          //goToHub();
          //scoreBall();
        }
        break;
    }
  }

  /**
   * Chooses the most confident ball detected.
   */
  public void chooseMostConfidentBall() {
    mostConfidentBallCoordinates = m_ballTracker.chooseMostConfidentBall();
    if (mostConfidentBallCoordinates != null) {
        SmartDashboard.putString("Most confident ball: ", mostConfidentBallCoordinates.toString());
    }
    else {
        SmartDashboard.putString("Most confident ball: ", "No ball located!");
    }
}

  /**
   * PID for turning
   * error is the target - actual.
   */
  public void PIDTurningControl(){
    try {
      error = setpoint - ((mostConfidentBallCoordinates.getXMin() + mostConfidentBallCoordinates.getXMax()) / 2); // Error = Target - Actual
      integral = (error * .02);
      //derivative = (error - previous_error) / .02;
      rcw = (kP * error + kI * integral);

      //Sensitivity adjustment, since the rcw value originally is in hundreds (it is the pixel error + integral).
      // 10.0 is an arbitrary number for testing, no real meaning behind it.
      if (rcw < 0) {
        rcw = -1 * Math.sqrt(Math.abs(rcw)) / 10.0;
      }
      else {
        rcw = Math.sqrt(rcw) / 10.0;
      }
      SmartDashboard.putString("values", rcw + " is rcw. " + "error is " + kP + ". integral is " + kI);
    }
    catch (Exception e) {
      SmartDashboard.putString("error", "runtime in pidturn");
    }
  }

  // +/- 10 degrees to allow for overshoot and undershot, will be redone with PID now.
  // Always call after chooseMostConfidentBall(), else mostConfidentBallCoordinates will be null
  public void turnToBall() {
    try {
    if (mostConfidentBallCoordinates != null) {
      SmartDashboard.putNumber("rcw in autocommand", rcw);
      m_driveTrain.arcadedrive(0, rcw);
    }
      SmartDashboard.putNumber("Yaw: ", m_imu.getYaw());
    } catch (Exception e) {
        logger.logError("Runtime Exception while trying to turnToBall() " + e);
        throw e;
    }
  }

  /**
   * Returns a value based on if the colorsensor detects a color or not. If it doesn't, it means there is no ball present.
   * TO BE TESTED.
   * @return
   */
  public boolean ballPickedUp() {
    return (m_colorSensor.checkColor().equals(""));
  } 
}
