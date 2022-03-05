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
import frc.robot.subsystems.RangeFinderSensor;
import frc.robot.commands.ArcadeDrive;

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
  private RangeFinderSensor m_rangeFinderSensor;

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
        m_driveTrain = m_robotContainer.getDriveTrain();
        m_rangeFinderSensor = m_robotContainer.getRangeFinder();
        m_chooser.setDefaultOption(Constants.ARCADE_DRIVE, Constants.ARCADE_DRIVE);
        m_chooser.addOption(Constants.TANK_DRIVE, Constants.TANK_DRIVE);
        m_chooser.addOption(Constants.CURVATURE_DRIVE, Constants.CURVATURE_DRIVE);
        SmartDashboard.putData("Drive Choices", m_chooser);

        // Sets Limelight to driver camera, turn off green LEDs.
        cam1 = new CameraSubsystem();
        cam1.setCamToDriverMode();
        cam1.setLedToOff();

        colorSensor = new ColorSensor();

      

        // turnClockwiseCommandDrive = new ArcadeDrive(() -> 0.4, () -> 0, m_driveTrain, Constants.JOYSTICK_THROTTLESPEED);
        // turnCounterClockwiseCommandDrive = new ArcadeDrive(() -> -0.4, () -> 0, m_driveTrain, Constants.JOYSTICK_THROTTLESPEED);
        // stopMotionCommandDrive = new ArcadeDrive(() -> 0.4, () -> 0, m_driveTrain, Constants.JOYSTICK_THROTTLESPEED);

        // Puts the calibrated yaw value, which should be around 0.0.
        
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
  public void disabledInit() {
    logger.cleanLogs(0);
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    try {
      m_autonomousCommand = m_robotContainer.getAutonomousCommand();

      // schedule the autonomous command (example)
      if (m_autonomousCommand != null) {
        m_autonomousCommand.schedule();
      }
    } catch (Exception e) {
      logger.logError("Runtime Exception in autonomousInit" + e);
      throw e;
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    try {
      logger.logInfo("Teleop init started");
      // This makes sure that the autonomous stops running when
      // teleop starts running. If you want the autonomous to
      // continue until interrupted by another command, remove
      // this line or comment it out.

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
      /*m_driveMode = m_chooser.getSelected();
      m_robotContainer.setDriveType(m_driveMode);*/
      SmartDashboard.putNumber("rangefinder values", m_rangeFinderSensor.getReading());
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

  public void turnToSpecifiedDegree() {
    // Turning to specified degrees
  }
}