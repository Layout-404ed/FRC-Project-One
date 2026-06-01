// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  //* Declare Motors here
  TalonFX myMoter = new TalonFX(0); // deviceID used to differentiate between motors, can find in tuner x

  public Robot() {
    m_robotContainer = new RobotContainer();
    
    //* Init Motors here
    myMoter.getConfigurator().apply(new TalonFXConfiguration()); // reset the motor to its factory default config

    // config the current of the motor (so it doesn't blow up)
    var currentConfig = new CurrentLimitsConfigs();
    currentConfig.StatorCurrentLimit = 80; // amps
    currentConfig.StatorCurrentLimitEnable = true;

    // refreshing then appying currentConfig
    myMoter.getConfigurator().refresh(currentConfig);
    myMoter.getConfigurator().apply(currentConfig);
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
    myMoter.set(.5); // range between -1.0 - 1.0 (percent, - = reveerse)
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
