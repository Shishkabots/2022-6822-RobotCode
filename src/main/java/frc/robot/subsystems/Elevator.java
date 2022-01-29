// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends SubsystemBase {

  private final TalonSRX m_ElevatorMotor;
  private ElevatorState m_elevatorState;

  public enum ElevatorState {
      IDLE, ON
  }

  public Elevator(){
    m_ElevatorMotor = new TalonSRX(Constants.ELEVATOR_MOTOR);
    m_elevatorState = ElevatorState.IDLE;
  }

  public void checkState() {
      switch(m_elevatorState) {
            case IDLE:
                setMotorSpeed(0.0);
                break;
            case ON:
                setMotorSpeed(SmartDashboard.getNumber("Desired Elevator Speed", 0.0));
                // Can possibly add color sensor to detect ball color + shoot to goal/away depending on color - would need color sensor
      }
  }
  
  // Sets elevator state to ON if the shooter is at the desired speed and ready to shoot.
  public void runElevator(boolean shooterReady) {
    if (shooterReady) {
      m_elevatorState = ElevatorState.ON;
      checkState();
    }
  }

  // Sets control mode to velocity such that motor runs at a specified velocity
  public void setMotorSpeed(double velocity){
    m_ElevatorMotor.set(ControlMode.Velocity, velocity);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Checks state to see if it has changed
    checkState();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}