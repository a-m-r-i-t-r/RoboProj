import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;

public class SpeedChanger implements Behavior{
	
	private BaseRegulatedMotor mL;
	private BaseRegulatedMotor mR;
	private boolean suppress = false;
	private EV3ColorSensor light;
	private int SPEED = 100;
	private float[] level = new float[1];
	
	SpeedChanger(BaseRegulatedMotor mL, BaseRegulatedMotor mR, EV3ColorSensor light) {
		this.mL = mL;
		this.mR = mR;
		this.light = light;
		
		mL.setSpeed(SPEED);
		mR.setSpeed(SPEED);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		SampleProvider ll = light.getColorID();
		
		while(!suppress) {
			
			ll.fetchSample(level, 0);
			float floaty = level[0];
			
			if (floaty == 1) {

				SPEED = SPEED * 1.2;
				mL.setSpeed(SPEED);
				mR.setSpeed(SPEED);
			}
			
			else if (floaty == 2) {
				SPEED = SPEED / 1.2;
				mL.setSpeed(SPEED);
				mR.setSpeed(SPEED);
			}
		}
	}

	@Override
	public void suppress() {
		suppress = true;
	}
}

