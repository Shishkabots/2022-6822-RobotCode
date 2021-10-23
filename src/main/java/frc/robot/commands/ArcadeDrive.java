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
  private double max_x, min_x;
  private double max_z, min_z;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public ArcadeDrive(DoubleSupplier speed, DoubleSupplier rotation, DriveTrain drivetrain, double sensitivity) {
    m_drivetrain = drivetrain;
    m_speed = () -> speed.getAsDouble() * sensitivity;
    m_rotation = () -> rotation.getAsDouble() * sensitivity;
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
    double xSpeed = m_speed.getAsDouble();
    double zRotation = m_rotation.getAsDouble();
    updateMaxMin(xSpeed, zRotation);

    m_drivetrain.arcadedrive(xSpeed, zRotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Arcadedrive ended, " + "interreupted = " + interrupted);

    //Print max & min speeds and rotation for debugging purposes
    System.out.println("Max-x = " + max_x + " Min-x = " + min_x);
    System.out.println("Max-x = " + max_z + " Min-x = " + min_z);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  

  }

  public void updateMaxMin(double xSpeed, double zRotation) {
    if (xSpeed > max_x) { max_x = xSpeed; }
    if (xSpeed < min_x) { min_x = xSpeed; }
    if (zRotation > max_z) { max_z = zRotation; }
    if (zRotation < min_z) { min_z = zRotation; }
  }


  

}
