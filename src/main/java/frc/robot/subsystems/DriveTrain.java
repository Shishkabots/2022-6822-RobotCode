// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {

  private final PWMSparkMax m_leftMotor = new PWMSparkMax(Constants.DRIVETRAIN_LEFT_MOTOR);
  private final PWMSparkMax m_rightMotor  = new PWMSparkMax(Constants.DRIVETRAIN_RIGHT_MOTOR);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  /**
   * @brief Arcade drive for differential drive platform.
   * @param xSpeed the robot's speed along x-axis
   * @param zRotation the robot's rotation rate around z-axis
   */
  public void drive(double xSpeed, double zRotation){
    m_robotDrive.arcadeDrive(xSpeed, zRotation);
  }

  /** Creates a new DriveTrain. */
  public DriveTrain() { }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


}
