// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.logging.RobotLogger;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.auto.BallTracker;
import frc.robot.subsystems.Imu;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 * 
 * Test comment to test gitignore, can remove.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private String m_driveMode;
  private RobotContainer m_robotContainer;
  private DriveTrain m_driveTrain;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final RobotLogger logger = RobotContainer.getLogger();
  private CameraSubsystem cam1;
  private ColorSensor colorSensor;
  private int m_logCounter;
  private BallTracker m_ballTracker;
  private Imu m_imu;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    try {
        // Log that robot has been initialized
        logger.logInfo("Robot initialized.");

        // Instantiate our RobotContainer. This will perform all our button bindings,
        // and put our
        // autonomous chooser on the dashboard.
        m_robotContainer = new RobotContainer();
        m_chooser.setDefaultOption(Constants.ARCADE_DRIVE, Constants.ARCADE_DRIVE);
        m_chooser.addOption(Constants.TANK_DRIVE, Constants.TANK_DRIVE);
        m_chooser.addOption(Constants.CURVATURE_DRIVE, Constants.CURVATURE_DRIVE);
        SmartDashboard.putData("Drive Choices", m_chooser);

        // Sets Limelight to driver camera, turn off green LEDs.
        cam1 = new CameraSubsystem();
        cam1.setCamToDriverMode();
        cam1.setLedToOff();

        colorSensor = new ColorSensor();

        m_ballTracker = new BallTracker();
        
        m_imu = new Imu(Constants.NAV_X_PORT);
        logger.logInfo("imu angle calibration:" + m_imu.getYaw());
    } catch (Exception e) {
        logger.logError("Runtime Exception in robotInit" + e);
        throw e;
    }
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    try {
      CommandScheduler.getInstance().run();
    } catch (Exception e) {
      logger.logError("Runtime Exception in robotPeriodic" + e);
      throw e;
    }
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    try {
      logger.logInfo("Autonomous initialized");
      m_autonomousCommand = m_robotContainer.getAutonomousCommand();

      // schedule the autonomous command (example)
      if (m_autonomousCommand != null) {
        m_autonomousCommand.schedule();
      }

      m_imu.reset();
    } catch (Exception e) {
      logger.logError("Runtime Exception in autonomousInit" + e);
      throw e;
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    try {
      if ((m_logCounter / 100.0) % 1 == 0) {
        if (m_ballTracker.chooseMostConfidentBall() != null) {
          logger.logInfo(m_ballTracker.chooseMostConfidentBall().toString());
        }
        else {
          logger.logInfo("No ball located!");
        }
      }

      // Turning to specified degrees
      if (m_imu.isCalibrating() == false && m_imu.getYaw() < Constants.targetDegrees) {
        m_driveTrain.arcadedrive(0.4, 0);
        logger.logInfo(""+m_imu.getYaw());
      }
      else if (m_imu.isCalibrating() == false && m_imu.getYaw() > Constants.targetDegrees) {
        m_driveTrain.arcadedrive(-0.4, 0);
      }
      else {
        logger.logInfo("Angle reached");
        m_driveTrain.arcadedrive(0, 0);
      }
    } catch (Exception e) {
        logger.logError("Runtime Exception in autonomousPeriodic" + e);
        throw e;
    }
  }

  @Override
  public void teleopInit() {
    try {
      logger.logInfo("Teleop init started");
      // This makes sure that the autonomous stops running when
      // teleop starts running. If you want the autonomous to
      // continue until interrupted by another command, remove
      // this line or comment it out.
      if (m_autonomousCommand != null) {
        m_autonomousCommand.cancel();
      }
      m_driveMode = m_chooser.getSelected();
      logger.logInfo("Drive Mode: " + m_driveMode);
    } catch (Exception e) {
        logger.logError("Runtime Exception in teleopInit" + e);
        throw e;
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    try {
      if (m_ballTracker.chooseMostConfidentBall() != null) {
        SmartDashboard.putString("Most confident ball: ", m_ballTracker.chooseMostConfidentBall().toString());
      }
      else {
        SmartDashboard.putString("Most confident ball: ", "No ball located!");
      }
      m_driveMode = m_chooser.getSelected();
      m_robotContainer.setDriveType(m_driveMode);

    } catch (Exception e) {
        logger.logError("Runtime Exception in teleopPeriodic" + e);
        throw e;
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    try {
      CommandScheduler.getInstance().cancelAll();
    } catch (Exception e) {
        logger.logError("Runtime Exception in testInit" + e);
        throw e;
    }
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
