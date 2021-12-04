// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class Intake extends SubsystemBase {
    
    private final WPI_TalonFX m_leftIntakeMotor;
    private final WPI_TalonFX m_rightIntakeMotor;

  /** Creates a new DriveTrain. */
  public Intake() {
    m_leftIntakeMotor = new WPI_TalonFX(Constants.INTAKE_LEFT_MOTOR);
    m_rightIntakeMotor =  new WPI_TalonFX(Constants.INTAKE_RIGHT_MOTOR);//idtthis gonanw ork 
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
