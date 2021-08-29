// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonFX;


public class DriveTrain extends SubsystemBase {

  private final PWMTalonFX m_leftFrontMotor;
  private final PWMTalonFX m_leftBackMotor;

  private final PWMTalonFX m_rightFrontMotor;
  private final PWMTalonFX m_rightBackMotor;

  private SpeedControllerGroup m_leftSide;
  private final SpeedControllerGroup m_rightSide;

  private final DifferentialDrive m_robotDrive;

  /**
   * @brief Arcade drive for differential drive platform.
   * @param xSpeed the robot's speed along x-axis
   * @param zRotation the robot's rotation rate around z-axis
   * test
   */
  public void drive(double xSpeed, double zRotation){
    m_robotDrive.arcadeDrive(xSpeed, zRotation);
  }

  /** Creates a new DriveTrain. */
  public DriveTrain() {

    m_leftFrontMotor = new PWMTalonFX(Constants.DRIVETRAIN_LEFT_FRONT_MOTOR);
    m_leftBackMotor = new PWMTalonFX(Constants.DRIVETRAIN_LEFT_BACK_MOTOR);
  
    m_rightFrontMotor  = new PWMTalonFX(Constants.DRIVETRAIN_RIGHT_FRONT_MOTOR);
    m_rightBackMotor  = new PWMTalonFX(Constants.DRIVETRAIN_RIGHT_BACK_MOTOR);

    m_leftSide = new SpeedControllerGroup(m_leftFrontMotor, m_leftBackMotor);
    m_rightSide = new SpeedControllerGroup(m_rightFrontMotor, m_rightBackMotor);

    m_robotDrive = new DifferentialDrive(m_leftSide, m_rightSide); 
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
