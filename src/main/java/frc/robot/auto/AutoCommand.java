package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Elevator;
import frc.robot.logging.RobotLogger;
import frc.robot.RobotContainer;

public class AutoCommand extends CommandBase {
    
    private Intake m_intake;
    private Shooter m_shooter;
    private DriveTrain m_driveTrain;
    private Elevator m_elevator;
    private final RobotLogger m_logger;

    /**
     * Creates a new AutoCommand command.
     * 
     * @param m_DriveTrain The drivetrain used by this command.
     * @param m_Intake The intake on the robot
     * @param m_Elevator The elevator on the robot
     * @param m_Shooter The shooter on the robot. 
     */
    public AutoCommand(DriveTrain m_DriveTrain, Intake m_Intake, Elevator m_Elevator, Shooter m_Shooter) {
        m_intake = m_Intake;
        m_driveTrain = m_DriveTrain;
        m_intake = m_Intake;
        m_elevator = m_Elevator;
        m_logger = RobotContainer.getLogger();

        addRequirements(m_driveTrain); 
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_logger.logInfo("Autonomous initialized");
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_driveTrain.tankdrive(0.1, 0.1);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_logger.logInfo("Arcadedrive interrupted");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        return false; // Runs until interrupted
    }
}
