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
	private float[] level = new float[1];
	
	FollowLine(BaseRegulatedMotor mL, BaseRegulatedMotor mR, EV3ColorSensor light, float lightUp, float lightLo) {
		this.mL = mL;
		this.mR = mR;
		this.light = light;
		this.lightUp = lightUp;
		this.lightLo = lightLo;
		
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		SampleProvider ll = light.getRedMode();
		
		while(!suppress) {
			
			ll.fetchSample(level, 0);
			float floaty = level[0];
			
			if (floaty < lightLo) {
				mL.forward();
				mR.flt();
			}
			
			else if (floaty > lightUp) {
				mR.forward();
				mL.flt();
			}
			
			else {
				mL.forward();
				mR.forward();
			}
			
		}
		
	}

	@Override
	public void suppress() {
		suppress = true;
	}

}