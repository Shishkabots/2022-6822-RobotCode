// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  // Use Talon SRX or FX for the motor
  private final VictorSPX m_intakeMotor;

  public Intake(){
    m_intakeMotor = new VictorSPX(Constants.INTAKE_LEAD_MOTOR);
  }

  // Sets control mode to velocity such that motor runs at a specified velocity
  public void setVelocityModeSpeed(double speed){
    m_intakeMotor.set(ControlMode.Velocity, speed);
  }

  public void setPercentModeSpeed(double speed) {
    m_intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public double intakeVelocity(){
    return m_intakeMotor.getSelectedSensorVelocity();
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