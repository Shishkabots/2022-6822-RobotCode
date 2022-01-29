// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  // Use Talon SRX or FX for the motor
  private final TalonSRX m_intakeMotor;
  private final TalonSRX m_intakeFollower;

  public Intake(){
    m_intakeMotor = new TalonSRX(Constants.INTAKE_LEAD_MOTOR);
    m_intakeFollower = new TalonSRX(Constants.INTAKE_FOLLOWER_MOTOR);

    // Inverts direction of follower motor since the lead and follower will face towards each other
    m_intakeFollower.setInverted(Constants.IS_INVERTED);
    m_intakeFollower.follow(m_intakeMotor);
  }

  // Sets control mode to velocity such that motor runs at a specified velocity
  public void setIntake(double velocity){
    m_intakeMotor.set(ControlMode.Velocity, velocity);
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