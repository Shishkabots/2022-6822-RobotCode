// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class NonPID extends CommandBase {
    private final DriveTrain m_drivetrain;
    private Double m_speed;
    // private Double m_currentDistance;
    private final Double m_targetDistance;

  /**
   * Creates a new ArcadeDrive command.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public NonPID(DriveTrain drivetrain, double targetdistance) {
    m_drivetrain = drivetrain;
    m_targetDistance = targetdistance;

    m_speed = 0.5;
    addRequirements(m_drivetrain);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Non-PID command initialized. Distance moved: " + m_targetDistance);
   }

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
    m_drivetrain.arcadedrive(m_speed, 0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("NonPIDdrive ended, interreupted = " + interrupted);
    m_drivetrain.arcadedrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double currentDistance = m_drivetrain.getEncoderPosition(0) / 2 + m_drivetrain.getEncoderPosition(1) / 2;
    System.out.println(m_targetDistance - currentDistance);
    System.out.println("Current" + currentDistance);
    if (currentDistance < m_targetDistance) {
      // System.out.println(m_speed);  
      m_speed = 0.5;    
      return false;
    } else {
        m_speed = 0.0;
        return true;
    }

  
  }  
}
