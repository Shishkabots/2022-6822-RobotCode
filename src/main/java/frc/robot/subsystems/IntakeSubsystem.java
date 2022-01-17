// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  private final TalonSRX INTAKE_MOTOR; //use srx or fx for the moter
  //https://binnur.gitbooks.io/spartronics-developers-handbook/content/commands_subsystems/
  private final TalonSRX intakeSlave;

  private final WPI_TalonFX m_leftFrontMotor;
  private final WPI_TalonFX m_leftBackMotor;

  private final WPI_TalonFX m_rightFrontMotor;
  private final WPI_TalonFX m_rightBackMotor;

  private SpeedControllerGroup m_leftSide;
  private final SpeedControllerGroup m_rightSide;

  private final DifferentialDrive m_robotDrive;

  public IntakeSubsystem(){
    INTAKE_MOTOR = new TalonSRX(Constants.INTAKE_MOTOR);
    intakeSlave = new TalonSRX(6);

    m_leftFrontMotor = new WPI_TalonFX(Constants.DRIVETRAIN_LEFT_FRONT_MOTOR);
    m_leftBackMotor = new WPI_TalonFX(Constants.DRIVETRAIN_LEFT_BACK_MOTOR);
    m_rightFrontMotor = new WPI_TalonFX(Constants.DRIVETRAIN_RIGHT_FRONT_MOTOR);
    m_rightBackMotor = new WPI_TalonFX(Constants.DRIVETRAIN_RIGHT_BACK_MOTOR);
    
    m_leftSide = new SpeedControllerGroup(m_leftFrontMotor, m_leftBackMotor);
    m_rightSide = new SpeedControllerGroup(m_rightFrontMotor, m_rightBackMotor);

    m_robotDrive = new DifferentialDrive(m_leftSide, m_rightSide); 

    intakeSlave.setInverted(true);
    intakeSlave.follow(INTAKE_MOTOR);
  }

  public void setIntake(double power){
    INTAKE_MOTOR.set(ControlMode.PercentOutput, power);
  }

  public double intakeVelocity(){
    return INTAKE_MOTOR.getSelectedSensorVelocity();
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