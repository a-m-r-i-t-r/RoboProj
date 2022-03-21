import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class Clap {
	
	public class ClapFilter implements SampleProvider {
		
		
		public int sampleSize() {
			return 0;
		}

		
		public void fetchSample(float[] sample, int offset) {
			
		}
		  

		public ClapFilter(SensorMode sound, float f, int i) {
			
		}

	public void main(String[] args) {
		
		boolean mainloop = true;
		boolean forward = false;
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(500);
		mRight.setSpeed(500);
		
		float[] start = new float[1];
		
		NXTSoundSensor sensor = new NXTSoundSensor(MotorPort.D);
		SensorMode sound = (SensorMode) sensor.getDBMode();
		SampleProvider clap = new ClapFilter(sound, 0.6f, 1000);
		
		while(mainloop) {
			 
			 clap.fetchSample(start, 0);
			 float soundy = start[0];
			 
			 LCD.drawString("GOOOOO", 3, 3);
			 Delay.msDelay(750);
			 LCD.clear();
			 
			 if (Button.DOWN.isDown()) {
				 mainloop = false;
			 }
			 
			 if (soundy > 0.6) {
				 forward = true;
			 }
			 
			 while(forward) {
				 
				 clap.fetchSample(start, 0);
				 soundy = start[0];
				 
				 mLeft.forward();
				 mRight.forward();
				 
				 
			 }
			 
		}
		 sensor.close();
		 mLeft.close();
		 mRight.close();
	}


	
		
	}
}