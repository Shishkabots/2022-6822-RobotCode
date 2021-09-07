// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {
  private final DriveTrain m_drivetrain;
  private final DoubleSupplier m_speed;
  private final DoubleSupplier m_rotation;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public ArcadeDrive(DoubleSupplier speed, DoubleSupplier rotation, DriveTrain drivetrain) {
    m_drivetrain = drivetrain;
    m_speed = speed;
    m_rotation = rotation;
    addRequirements(m_drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ArcadeDrive initialized");
   }

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
    m_drivetrain.arcadedrive(m_speed.getAsDouble(), m_rotation.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Arcadedrive ended" + interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  
  }


  
}
