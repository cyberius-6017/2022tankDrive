// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;


/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {

  private final XboxController m_controller = new XboxController(0);

  private final WPI_VictorSPX frontLeftMotor = new WPI_VictorSPX(3);
  private final WPI_VictorSPX rearLeftMotor = new WPI_VictorSPX(4);
  private final PWMVictorSPX frontRightMotor = new PWMVictorSPX(0);
  private final WPI_VictorSPX rearRightMotor = new WPI_VictorSPX(2);

  private final MotorControllerGroup rightGroup = new MotorControllerGroup(frontRightMotor, rearRightMotor);
  private final MotorControllerGroup leftGroup = new MotorControllerGroup(frontLeftMotor, rearLeftMotor);

  // motor control variable defining
  static double rightHistoric = 0;                               
  static double leftHistoric = 0;
  static double leftInput = 0;
  static double rightInput = 0;

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    rightGroup.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    
    //differential drive
    rightInput = -0.5 * m_controller.getLeftY()- 0.3 * m_controller.getRightX();      
    leftInput = -0.5 * m_controller.getLeftY() + 0.3 * m_controller.getRightX();

    //speed limiter
    rightInput = rightInput * 0.9;                                                      
    leftInput = leftInput * 0.9;
    

    //ramp (para proteger los motores)
    rightHistoric = rightHistoric + 0.08 * (rightInput - rightHistoric);                 
    leftHistoric = leftHistoric + 0.08 * (leftInput - leftHistoric);


    //activiating motor groups 
    leftGroup.set(leftHistoric);                                                        
    rightGroup.set(rightHistoric);
  }
}

