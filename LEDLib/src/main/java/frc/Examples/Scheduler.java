package frc.Examples;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import frc.LLColor;
import frc.LedScheduler;
import frc.LedStrip;
import frc.Animations.Animation;
import frc.Animations.BounceAnimation;
import frc.Animations.RainbowFadeAnimation;
import frc.Animations.SolidColorPattern;
import frc.Utils.PredefinedColors;

public class Scheduler extends LedScheduler {
    private Animation ifNotConnected;
    private Animation ifDisabled;
    private Animation ifAuton;
    private Animation ifTeleop;
    private Animation ifEStoped;
    private Animation ifAllElseFails;
    public Scheduler(LedStrip ledStrip){
        super(ledStrip);
        ifNotConnected = new SolidColorPattern(ledStrip, PredefinedColors.kAqua);
        ifDisabled = new BounceAnimation(ledStrip, PredefinedColors.kAqua, PredefinedColors.kDarkViolet, 5);
        ifAuton = new RainbowFadeAnimation(ledStrip);
        //TODO:
        ifTeleop = null;
        ifEStoped = new SolidColorPattern(ledStrip, (LLColor)Color.kDarkRed);
        ifAllElseFails = new SolidColorPattern(ledStrip, (LLColor)Color.kWhite);
    }

    @Override
    public Animation getAnimation() {
        if(!DriverStation.isDSAttached()){
            return ifNotConnected;
        }

        else if(DriverStation.isDisabled()){
            return ifDisabled;
        }

        else if(DriverStation.isTeleop()){
            return ifTeleop;
        }

        else if(DriverStation.isAutonomous()){
            return ifAuton;
        }
        else if(DriverStation.isEStopped()){
            return ifEStoped;
        }
        else{
            return ifAllElseFails;
        }
    }

    
    
}
