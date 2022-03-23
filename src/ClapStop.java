import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.SampleProvider;

public class ClapStop implements Behavior{
	
	private BaseRegulatedMotor mL;
	private BaseRegulatedMotor mR;
	private boolean suppress = false;
	private NXTSoundSensor sound;
	private float[] level = new float[1];
	
	ClapStop(BaseRegulatedMotor mL, BaseRegulatedMotor mR, NXTSoundSensor sound) {
		this.mL = mL;
		this.mR = mR;
		this.sound = sound;
		
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		SampleProvider clap = sound.getDBMode();
		
		while(!suppress) {
			
			clap.fetchSample(level, 0);
			float floaty = level[0];
			
			if (floaty > 0.6) {
				mL.stop();
				mR.stop();
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