// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto;

import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.logging.RobotLogger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Imu;
import frc.robot.subsystems.DriveTrain;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.BallTracker;

/** An example command that uses an example subsystem. */
public class AutoCommand extends CommandBase {

    private Imu m_imu;
    private DriveTrain m_driveTrain;
    private BallTracker m_ballTracker;
    private BallCoordinates mostConfidentBallCoordinates;

  private final RobotLogger logger = RobotContainer.getLogger();

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param  drivetrain The drivetrain used by this command.
   */
  public AutoCommand(Imu imu, DriveTrain driveTrain, BallTracker ballTracker) {
    m_imu = imu;
    m_driveTrain = driveTrain;
    m_ballTracker = ballTracker;
    addRequirements(m_imu, m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    logger.logInfo("Autonomous Command initialized!");
    m_imu.reset();
   }

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
      chooseMostConfidentBall();
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


  public void chooseMostConfidentBall() {
    mostConfidentBallCoordinates = m_ballTracker.chooseMostConfidentBall();
    if (mostConfidentBallCoordinates != null) {
        SmartDashboard.putString("Most confident ball: ", mostConfidentBallCoordinates.toString());
    }
    else {
        SmartDashboard.putString("Most confident ball: ", "No ball located!");
    }
}

  // +/- 10 degrees to allow for overshoot and undershot, will be redone with PID now.
  // Always call after chooseMostConfidentBall(), else mostConfidentBallCoordinates will be null
  public void turnToBall() {
    try {
    if (mostConfidentBallCoordinates != null) {
    if (Constants.CAMERA_WIDTH_IN_PIXELS_OVER_TWO > mostConfidentBallCoordinates.getXMin() && Constants.CAMERA_WIDTH_IN_PIXELS_OVER_TWO < mostConfidentBallCoordinates.getXMax()) {
        logger.logInfo("Ball reached");
        SmartDashboard.putString(Constants.DIRECTION_KEY, "Stopped Turning");
        m_driveTrain.setAutoTurnDirection(Constants.STOP_TURNING);
        SmartDashboard.putNumber("a", mostConfidentBallCoordinates.getXMin());
      }
      else if (m_imu.isCalibrating() == false && Constants.CAMERA_WIDTH_IN_PIXELS_OVER_TWO < mostConfidentBallCoordinates.getXMin()) {
        SmartDashboard.putString(Constants.DIRECTION_KEY, "Clockwise");
        m_driveTrain.setAutoTurnDirection(Constants.CLOCKWISE);
        SmartDashboard.putNumber("a", mostConfidentBallCoordinates.getXMin());
      }
      else if (m_imu.isCalibrating() == false && Constants.CAMERA_WIDTH_IN_PIXELS_OVER_TWO > mostConfidentBallCoordinates.getXMin()) {
        SmartDashboard.putString(Constants.DIRECTION_KEY, "Counter clockwise");
        m_driveTrain.setAutoTurnDirection(Constants.COUNTER_CLOCKWISE);
        SmartDashboard.putNumber("a", mostConfidentBallCoordinates.getXMin());

      }}
      SmartDashboard.putNumber("Yaw: ", m_imu.getYaw());
    } catch (Exception e) {
        logger.logError("Runtime Exception while trying to turnToBall() " + e);
        throw e;
    }
  }
}
