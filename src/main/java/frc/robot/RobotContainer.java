// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.CurvatureDrive;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Imu;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import java.util.function.BooleanSupplier;
import frc.robot.logging.RobotLogger;
import java.util.logging.Level;
import java.io.IOException;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.auto.AutoCommand;
import frc.robot.auto.BallTracker;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's commands, subsystems, and IO devices are defined here...
  public enum DriveType {
    ARCADE_DRIVE,
    TANK_DRIVE,
    CURVATURE_DRIVE
  }

  private Command m_autoCommand;
  private Command m_teleopCommand;
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Shooter m_shooter = new Shooter();
  private final Joystick m_driverStick = new Joystick(Constants.DRIVER_STICK_PORT);
  private final Imu m_imu = new Imu();
  private final BallTracker m_ballTracker = new BallTracker();
  private DriveType m_driveType = DriveType.ARCADE_DRIVE;
  private static RobotLogger logger;
  private final Intake m_intake = new Intake();

  // True makes it turn-in-place, false makes it do constant-curvature motion.
  private final BooleanSupplier m_isQuickTurn = () -> false; 
 
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // assign default commands

    m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_THROTTLESPEED));

    m_intake.setDefaultCommand(new RunCommand(() -> SmartDashboard.putNumber("Intake Velocity", m_intake.intakeVelocity()), m_intake));


    m_autoCommand = new AutoCommand(m_imu, m_drivetrain, m_ballTracker);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Removes speed throttling during ArcadeDrive, allows robot to move at max speed.
    new JoystickButton(m_driverStick, Constants.JOYSTICK_RIGHTTRIGGER).whenHeld(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_FULLSPEED));

    // Activates shooter upon holding left trigger, can modify depending on how we want to activate shooter.
    new JoystickButton(m_driverStick, Constants.JOYSTICK_LEFTTRIGGER).whileHeld(new Shoot(m_shooter));
    
    /**
     * While holding the left bumper, the intake will turn on and run at the desired speed from SmartDash.
     */
    new JoystickButton(m_driverStick, Constants.JOYSTICK_RIGHTBUMPER).whileHeld(
      new StartEndCommand(
        () -> m_intake.setIntake(SmartDashboard.getNumber("Set Intake Velocity", 0.0)),
        () -> m_intake.setIntake(0), m_intake));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public Command getTeleopCommand(){
    return m_teleopCommand;
  }

  /**
   * Accessor methods 
   */
  public DriveType getDriveType() {
    return m_driveType;
  }

  // Initiates logger
    private static void initLogger(RobotLogger log) {
    try {
        log.init(RobotContainer.class);
        log.setLevel(Level.INFO);

        log.cleanLogs(Constants.LOG_EXPIRATION_IN_HRS);
        log.logInfo("Logger initialized");
    } 
    catch (IOException error) {
      log.logError("Failed to init logger!");
      throw new RuntimeException(error);
    }
  }

  //Gets logger
  public static RobotLogger getLogger() {
    if (logger == null) {
      logger = new RobotLogger();
      initLogger(logger);
    }
    return logger;
  }

  /**
   * When switching you must press right trigger to initiate the change.
   * Though right trigger turns ArcadeDrive to full throttle, once you let go it will initiate the driver mode on SmartDashboard.
   */
  public void checkDrivetype() {
    switch(m_driveType) {
      case ARCADE_DRIVE:
        m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_THROTTLESPEED)); 
        break;
      case TANK_DRIVE:
        m_drivetrain.setDefaultCommand(new TankDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain));
        break;
      case CURVATURE_DRIVE:
        m_drivetrain.setDefaultCommand(new CurvatureDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_isQuickTurn, m_drivetrain));
        break;
      default:
        m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_THROTTLESPEED)); 
    }
  }

  /**
   * Sets the drive type based on the string passed in (called in after getting SendableChooser in Robot.java)
   * Also runs the checkDriveType() method, to change the drivetype if necessary.
   */ 
  public void setDriveType(String driveType) {
    if(Constants.TANK_DRIVE.equalsIgnoreCase(driveType)) {  
      m_driveType = DriveType.TANK_DRIVE;
    }
    else if(Constants.CURVATURE_DRIVE.equalsIgnoreCase(driveType)) {
      m_driveType = DriveType.CURVATURE_DRIVE;
    }
    else {
      m_driveType = DriveType.ARCADE_DRIVE;
    }

    checkDrivetype();
  }
  public DriveTrain getDriveTrain() {
    return m_drivetrain;
  }
}
