import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Main {
	
	// change the version number - 1.0 will be the final version
	final static String VERSION = "1.0";
	
	// constants
	final static int WHEEL_DIA = 56;
	final static int LINEAR_SPEED = 70;
	final static int ANGULAR_SPEED = 50;
	final static int AXLE_LEN = 130;
	final static int SPEED = 100;

	public static void main(String[] args) {
		
		// welcome message
		LCD.drawString("Ali, Amrit, Ash, Kamil", 1, 1);
		LCD.drawString("Version: " + VERSION, 1, 2);
		LCD.drawString("Please press a button", 1, 1);
		
		// infinite delay until enter is pressed
		while(!Button.ENTER.isDown()) {
			Delay.msDelay(100);
		}
		
		LCD.clear();

		// THE MAIN PROGRAM
		
		// the sensors u,c,c,s
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		EV3ColorSensor cs1 = new EV3ColorSensor(SensorPort.S2);
		EV3ColorSensor cs2 = new EV3ColorSensor(SensorPort.S3);
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		
		// initializing the wheels
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A); // left wheel in A
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B); // right wheel in B
				
		mL.setSpeed(SPEED);
		mR.setSpeed(SPEED);
		
		// sample providers
		SampleProvider dm = us.getDistanceMode(); // object detection
		SampleProvider rm = cs1.getRedMode(); // line follow sensor
		SampleProvider cm = cs2.getRGBMode(); // colour detection sensor
		SampleProvider sm = ss.getDBAMode(); // clap sensor
		
		// samples array
		float[] levels = new float[4];
		
		// calibration
		float maxL = 0.0f;
		float minL = 1.0f;
		
		while(!Button.DOWN.isDown()) {
			rm.fetchSample(levels, 1);
			
			if(levels[1] > maxL) {
				maxL = levels[1];
			}
			
			if (levels[1] < minL) {
				minL = levels[1];
			}
			
			LCD.drawString("Max =" + Float.toString(maxL), 2, 2);
			LCD.drawString("Press 'Down' to complete calibration",  2, 4);
			LCD.drawString("Min =" + Float.toString(minL), 2, 6);
			
			Delay.msDelay(500);
			
			LCD.clear();
		}
		
		// exact average - PREFERABLE USE APPROX
		float lightAv = ((maxL + minL)/2);
		// approx averages
		float lightUp = lightAv + (lightAv * 0.1f);
		float lightLo = lightAv - (lightAv * 0.1f);
		
		// behaviours
		Behavior first = new FollowLine(mL, mR, cs1, lightUp, lightLo);
		
		// arbitrator
		Behavior[] behavs = {first};
		Arbitrator arb = new Arbitrator(behavs);
		arb.go();
	}

}
