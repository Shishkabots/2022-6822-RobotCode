// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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
    SmartDashboard.putNumber("rcw = ", zRotation);
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

    m_leftFrontMotor.clearStickyFaults();
    m_leftBackMotor.clearStickyFaults();
    m_rightFrontMotor.clearStickyFaults();
    m_rightBackMotor.clearStickyFaults();

    m_leftFrontMotor.configFactoryDefault();
    m_leftBackMotor.configFactoryDefault();
    m_rightBackMotor.configFactoryDefault();
    m_rightFrontMotor.configFactoryDefault();

    m_leftFrontMotor.setNeutralMode(NeutralMode.Coast);
    m_leftBackMotor.setNeutralMode(NeutralMode.Coast);
    m_rightFrontMotor.setNeutralMode(NeutralMode.Coast);
    m_rightBackMotor.setNeutralMode(NeutralMode.Coast);

    /**
     * Inverts all motors based on value of the constant, which is currently true.
     * Currently the robot goes battery-first which is dangerous, so this will flip that direction.
     */
    m_leftFrontMotor.setInverted(Constants.IS_INVERTED);
    m_leftBackMotor.setInverted(Constants.IS_INVERTED);

    
    m_leftSide = new MotorControllerGroup(m_leftFrontMotor, m_leftBackMotor);
    m_rightSide = new MotorControllerGroup(m_rightFrontMotor, m_rightBackMotor);

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

  public void setAutoTurnDirection(int direction) {
    /*if (direction == Constants.STOP_TURNING) {
        m_leftFrontMotor.set(0);
        m_rightSide.set(0);
    }*/
   if (direction == Constants.CLOCKWISE) {
      m_leftFrontMotor.set(ControlMode.PercentOutput, -0.15);
      m_rightFrontMotor.set(ControlMode.PercentOutput, 0.15);
      m_rightBackMotor.set(ControlMode.PercentOutput, 0.15);
    }
    else if (direction == Constants.COUNTER_CLOCKWISE) {
        m_leftFrontMotor.set(ControlMode.PercentOutput, 0.15);
        m_rightFrontMotor.set(ControlMode.PercentOutput, -0.15);
        m_rightBackMotor.set(ControlMode.PercentOutput, -0.15);
    }
  }
} 