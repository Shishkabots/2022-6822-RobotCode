// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveTrain extends SubsystemBase {

  private final WPI_TalonFX m_leftFrontMotor;
  private final WPI_TalonFX m_leftBackMotor;

  private final WPI_TalonFX m_rightFrontMotor;
  private final WPI_TalonFX m_rightBackMotor;

  private MotorControllerGroup m_leftSide;
  private final MotorControllerGroup m_rightSide;

  private final DifferentialDrive m_robotDrive;
  private final double m_startingEncoderPositionLeftFront;
  private final double m_startingEncoderPositionLeftBack;
  private final double m_startingEncoderPositionRightFront;
  private final double m_startingEncoderPositionRightBack;

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
  public double getEncoderPosition(int position) {
    double returnValue = 0.0;
    switch(position) {
      case 0:
        double leftFrontMotor = m_leftFrontMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionLeftFront;
        double distanceLeft = leftFrontMotor/ Constants.ENCODER_COUNT_TO_METERS;
        returnValue = distanceLeft;
        break;
      case 1:
        double RightFrontMotor = m_leftFrontMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionLeftFront;
        double distanceRight = RightFrontMotor/ Constants.ENCODER_COUNT_TO_METERS;
        returnValue = distanceRight;
        break;
      default:
        System.out.println("None choosed. Position: " + position);
        break;



    }
    return returnValue;

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

    m_leftFrontMotor.configFactoryDefault();
    m_rightFrontMotor.configFactoryDefault();
    m_leftBackMotor.configFactoryDefault();
    m_rightBackMotor.configFactoryDefault();

    m_leftSide = new MotorControllerGroup(m_leftFrontMotor, m_leftBackMotor);
    m_rightSide = new MotorControllerGroup(m_rightFrontMotor, m_rightBackMotor);


    m_robotDrive = new DifferentialDrive(m_leftSide, m_rightSide); 
    
    m_startingEncoderPositionLeftFront = m_leftFrontMotor.getSelectedSensorPosition(0);
    m_startingEncoderPositionRightFront = m_rightFrontMotor.getSelectedSensorPosition(0);
    m_startingEncoderPositionLeftBack = m_leftBackMotor.getSelectedSensorPosition(0);
    m_startingEncoderPositionRightBack = m_rightBackMotor.getSelectedSensorPosition(0);
    
  }

  @Override
  public void periodic() {
    double selSenPosLeftFront = m_leftFrontMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionLeftFront;
    double selSenPosRightFront = m_rightFrontMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionRightFront;
    double selSenPosLeftBack = m_leftBackMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionLeftBack;
    double selSenPosRightBack = m_rightBackMotor.getSelectedSensorPosition(0) - m_startingEncoderPositionRightBack;
    
    double selSenDisLeftFront = selSenPosLeftFront / Constants.ENCODER_COUNT_TO_METERS;
    double selSenDisRightFront = selSenPosRightFront / Constants.ENCODER_COUNT_TO_METERS;
    double selSenDisLeftBack = selSenPosLeftBack / Constants.ENCODER_COUNT_TO_METERS;
    double selSenDisRightBack = selSenPosRightBack / Constants.ENCODER_COUNT_TO_METERS;
    
    SmartDashboard.putNumber("Encoder position, LF: ", selSenPosLeftFront);
    SmartDashboard.putNumber("Theoritical Distance, LF:", selSenDisLeftFront);
    SmartDashboard.putNumber("Encoder position, RF: ", selSenPosRightFront);
    SmartDashboard.putNumber("Theoritical Distance, RF:", selSenDisRightFront);
    SmartDashboard.putNumber("Encoder position, LB: ", selSenPosLeftBack);
    SmartDashboard.putNumber("Theoritical Distance, LB:", selSenDisLeftBack);
    SmartDashboard.putNumber("Encoder position, RB: ", selSenPosRightBack);
    SmartDashboard.putNumber("Theoritical Distance, RB:", selSenDisRightBack);

    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
