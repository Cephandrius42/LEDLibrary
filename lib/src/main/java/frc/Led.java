// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc;

import java.util.HashMap;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.simulation.AddressableLEDSim;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Animations.Animation;

public class Led extends SubsystemBase {
  private final AddressableLED led;
  private final AddressableLEDBuffer ledBuffer;
  private final AddressableLEDSim ledSim;
  private Animation currentAnimation;
  private boolean runningAutomation = false;
  /** Creates a new Led. */
  public Led(int DIO_Port, int stripLength) {
    ledBuffer = new AddressableLEDBuffer(stripLength);
    led = new AddressableLED(DIO_Port);
    ledSim = new AddressableLEDSim(led);
    led.setLength(ledBuffer.getLength());
    setEntireStripToColor(Color.kBlack);
    led.start();
  }

  public Led(int DIO_Port, int stripLength, Color colorToIntializeTo) {
    ledBuffer = new AddressableLEDBuffer(stripLength);
    led = new AddressableLED(DIO_Port);
    ledSim = new AddressableLEDSim(led);
    ledSim.setRunning(true);
    led.setLength(ledBuffer.getLength());
    setEntireStripToColor(colorToIntializeTo);
    led.start();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(runningAutomation)
      setStripToMultipleColors(currentAnimation.generatePattern());

    
  }

  public void setEntireStripToColor(Color color){
    runningAutomation = false;
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setLED(i, color);
    }
    led.setData(ledBuffer);
  }

  HashMap<Color, Integer> rangesToSetToColors = new HashMap<>();

  public void setStripToMultipleColors(HashMap<Integer, Color> rangesToSetToColors){
    runningAutomation = false;
    int stripPosition = 0;
    for(int colorEndPoint : rangesToSetToColors.keySet()){
      for (; stripPosition < colorEndPoint; stripPosition++) {
        ledBuffer.setLED(stripPosition, rangesToSetToColors.get(colorEndPoint));
      }
      led.setData(ledBuffer);
    }
  }
  
  public void setAnimation(Animation animation){
    currentAnimation = animation;
    currentAnimation.reset();
    runningAutomation = true;
  }

  public void startAnimation(){
    runningAutomation = true;
    currentAnimation.reset();
  }
  public void stopAnimation(){
    runningAutomation = false;
    currentAnimation.reset();
  }

  public int getStripLength(){
    return ledBuffer.getLength();
  }
}