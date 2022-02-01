// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utilities;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends SubsystemBase {

  private final WPI_TalonFX m_leftFrontMotor;
  private final WPI_TalonFX m_leftBackMotor;

  private final WPI_TalonFX m_rightFrontMotor;
  private final WPI_TalonFX m_rightBackMotor;

  private MotorControllerGroup m_leftSide;
  private final MotorControllerGroup m_rightSide;

  private final DifferentialDrive m_robotDrive;

  /**
   * @brief Arcade drive for differential drive platform.
   * @param xSpeed the robot's speed along x-axis
   * @param zRotation the robot's rotation rate around z-axis
   * test
   */
  public void arcadedrive(double xSpeed, double zRotation){
    m_robotDrive.arcadeDrive(xSpeed, zRotation);
  }
  public void tankdrive(double leftSpeed, double rightSpeed) {
    m_robotDrive.tankDrive(leftSpeed, rightSpeed * -1);
  }
 
  public void curvaturedrive(double curveSpeed, double curveRotation, boolean isQuickTurn) {
    m_robotDrive.curvatureDrive(curveSpeed, curveRotation, isQuickTurn);
  }


  /** Creates a new DriveTrain. */
  public DriveTrain() {
    
    m_leftFrontMotor = new WPI_TalonFX(Constants.DRIVETRAIN_LEFT_FRONT_MOTOR);
    m_leftBackMotor = new WPI_TalonFX(Constants.DRIVETRAIN_LEFT_BACK_MOTOR);
    m_rightFrontMotor = new WPI_TalonFX(Constants.DRIVETRAIN_RIGHT_FRONT_MOTOR);
    m_rightBackMotor = new WPI_TalonFX(Constants.DRIVETRAIN_RIGHT_BACK_MOTOR);

    // Sets all internal encoders to 0, so that we can increase encoder values after this point and get accurate distance values.
    Utilities.setMotorEncoderToZero(m_leftFrontMotor, m_leftBackMotor, m_rightFrontMotor, m_rightBackMotor);

    /**
     * Inverts all motors based on value of the constant, which is currently true.
     * Currently the robot goes battery-first which is dangerous, so this will flip that direction.
     */
    //m_leftFrontMotor.setInverted(Constants.IS_INVERTED);
    //m_leftBackMotor.setInverted(Constants.IS_INVERTED);
    m_rightFrontMotor.setInverted(Constants.IS_INVERTED);
    m_rightBackMotor.setInverted(Constants.IS_INVERTED);

    
    m_leftSide = new MotorControllerGroup(m_leftFrontMotor, m_leftBackMotor);
    m_rightSide = new MotorControllerGroup(m_rightFrontMotor, m_rightBackMotor);

    m_robotDrive = new DifferentialDrive(m_leftSide, m_rightSide); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Robot position", Utilities.getEncoderPositionInMeters(m_leftBackMotor, m_leftFrontMotor, m_rightBackMotor, m_rightFrontMotor));
    SmartDashboard.putNumber("Robot velocity", Utilities.getEncoderVelocityInMetersPerSecond(m_leftBackMotor, m_leftFrontMotor, m_rightBackMotor, m_rightFrontMotor));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
