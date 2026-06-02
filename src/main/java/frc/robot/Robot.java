// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  //* Declare Motors here
  TalonFX myMoter = new TalonFX(0); // deviceID used to differentiate between motors, can find in tuner x

  TalonFX primaryMotor = new TalonFX(1);
  TalonFX followerMotor1 = new TalonFX(2);
  TalonFX followerMotor2 = new TalonFX(3);

  TalonFX leftMotor = new TalonFX(4);
  TalonFX rightMotor = new TalonFX(5);

  public Robot() {
    m_robotContainer = new RobotContainer();
    
    //* Init Motors here
    myMoter.getConfigurator().apply(new TalonFXConfiguration()); // reset the motor to its factory default config

    primaryMotor.getConfigurator().apply(new TalonFXConfiguration());
    followerMotor1.getConfigurator().apply(new TalonFXConfiguration());
    followerMotor2.getConfigurator().apply(new TalonFXConfiguration());

    leftMotor.getConfigurator().apply(new TalonFXConfiguration());
    rightMotor.getConfigurator().apply(new TalonFXConfiguration());

    // config the current of the motor (so it doesn't blow up)
    var currentConfig = new CurrentLimitsConfigs();
    currentConfig.StatorCurrentLimit = 80; // amps
    currentConfig.StatorCurrentLimitEnable = true;

    // refreshing then appying currentConfig
    myMoter.getConfigurator().refresh(currentConfig);
    primaryMotor.getConfigurator().refresh(currentConfig);
    followerMotor1.getConfigurator().refresh(currentConfig);
    followerMotor2.getConfigurator().refresh(currentConfig);
    leftMotor.getConfigurator().refresh(currentConfig);
    rightMotor.getConfigurator().refresh(currentConfig);

    myMoter.getConfigurator().apply(currentConfig);
    primaryMotor.getConfigurator().apply(currentConfig);
    followerMotor2.getConfigurator().apply(currentConfig);
    followerMotor2.getConfigurator().apply(currentConfig);
    leftMotor.getConfigurator().apply(currentConfig);
    rightMotor.getConfigurator().apply(currentConfig);

    // configure the follower motors
    followerMotor1.setControl(new Follower(1, MotorAlignmentValue.Aligned)); // no longer a boolean for invert
    followerMotor1.setControl(new Follower(1, MotorAlignmentValue.Aligned)); // now is MotorAlignmentValue.Aligned

    // rightMotor.setInverted(true); //! no longer works, ig was removed
    rightMotor.setControl(new Follower(4, MotorAlignmentValue.Opposed)); // is the way to do it now
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    //* Make Motors Do stuff here
    myMoter.set(.5); // range between -1.0 - 1.0 (percent, - = reverse)

    primaryMotor.set(.5); // dont need to set for followers, will automatically since they are following this

    leftMotor.set(.5);
    // rightMotor.set(.5); //! no longer needed cus the other function doesnt work so i just made it an inverted follower
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
