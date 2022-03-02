import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;

public class FollowLine implements Behavior{
	
	private BaseRegulatedMotor mL;
	private BaseRegulatedMotor mR;
	private boolean suppress = false;
	private EV3ColorSensor light;
	private float lightUp;
	private float lightLo;
	
	FollowLine(BaseRegulatedMotor mL, BaseRegulatedMotor mR, EV3ColorSensor light, float lightUp, float lightLo) {
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
//		if (floaty < (lightAverage - APPROX)) {
//			mLeft.forward();
//			mRight.flt();
//		} 
//		
//		else if(floaty > (lightAverage + APPROX)) {
//			mRight.forward();
//			mLeft.flt();
//		}
//		
//		else {
//			mLeft.forward();
//			mRight.forward();
//		}
	}

	@Override
	public void suppress() {
		suppress = true;
	}

}
