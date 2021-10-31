// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.CurvatureDrive;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import java.util.function.BooleanSupplier;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's commands, subsystems, and IO devices are defined here...

  private enum DriveType {
    ARCADE_DRIVE,
    TANK_DRIVE,
    CURVATURE_DRIVE
  }

  private Command m_autoCommand;
  private Command m_teleopCommand;
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Joystick m_driverStick = new Joystick(Constants.DRIVER_STICK_PORT);
  private DriveType m_driveType = DriveType.ARCADE_DRIVE;

  private final BooleanSupplier m_isQuickTurn = () -> false; //true makes it turn-in-place, false makes it do constant-curvature motion
 
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // assign default commands
    switch(m_driveType) {
      case ARCADE_DRIVE:
        m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_THROTTLESPEED)); 
        System.err.println("arcadedrive used");
        break;
      case TANK_DRIVE:
        m_drivetrain.setDefaultCommand(new TankDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain));
        System.err.println("tankdrive used");  
        break;
      case CURVATURE_DRIVE:
        m_drivetrain.setDefaultCommand(new CurvatureDrive(() -> (-m_driverStick.getY()), () -> m_driverStick.getX(), m_isQuickTurn, m_drivetrain));
        System.err.println("curvaturedrive used");
        break;
      default:
        m_drivetrain.setDefaultCommand(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_THROTTLESPEED));
        System.err.println("default used");          
    }
    
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
    new JoystickButton(m_driverStick, Constants.JOYSTICK_RIGHTTRIGGER).whenHeld(new ArcadeDrive(() -> (-m_driverStick.getRawAxis(Constants.JOYSTICK_LEFT_Y)), () -> m_driverStick.getRawAxis(Constants.JOYSTICK_RIGHT_X), m_drivetrain, Constants.JOYSTICK_FULLSPEED));
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

  public void setDriveType(String driveType) {
    if(driveType == "Tank Drive") {
      m_driveType = DriveType.TANK_DRIVE;
    }
    else {
      m_driveType = DriveType.ARCADE_DRIVE;
    }
  }
}
