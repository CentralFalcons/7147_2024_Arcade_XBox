// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {

  private WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(1);
  private WPI_TalonSRX m_rearLeft = new WPI_TalonSRX(2);
  private WPI_TalonSRX m_frontRight = new WPI_TalonSRX(3);
  private WPI_TalonSRX m_rearRight = new WPI_TalonSRX(4);

  private MotorController m_shooterFront = new WPI_VictorSPX(5);
  private MotorController m_shooterRear = new WPI_VictorSPX(6);

  private DifferentialDrive m_robotDrive = new DifferentialDrive(m_frontLeft::set, m_frontRight::set);

  private XboxController driver_controller = new XboxController(0);
  private XboxController shooter_controller = new XboxController(1);

  private static double INPUT_SPEED = .25;
  private static double OUTPUT_SPEED = .25;

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_frontLeft);
    SendableRegistry.addChild(m_robotDrive, m_rearLeft);
    SendableRegistry.addChild(m_robotDrive, m_frontRight);
    SendableRegistry.addChild(m_robotDrive, m_rearRight);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rearLeft.set(TalonSRXControlMode.Follower,1);
    m_rearRight.set(TalonSRXControlMode.Follower,3);
    m_frontRight.setInverted(true);
    m_rearRight.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(-driver_controller.getRightY(), -driver_controller.getLeftX()*.5);

    if(shooter_controller.getYButton()){
      m_shooterFront.set(OUTPUT_SPEED);
      m_shooterRear.set(OUTPUT_SPEED);
    } else if(shooter_controller.getAButton()){
      m_shooterFront.set(INPUT_SPEED);
      m_shooterRear.set(INPUT_SPEED);
    } else {
      m_shooterFront.set(0);
      m_shooterRear.set(0);
    }
  }
}
