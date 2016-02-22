package org.AkuGanew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class AudioObserver implements Observer{

	/**
	 * 
	 */
	static String foreGroundPath = "C:\\Users\\Paul\\workspace\\AkuGa\\sounds\\Vordergrund\\";
	static String backGroundPath = "C:\\Users\\Paul\\workspace\\AkuGa\\sounds\\Hintergrund\\";
	static FloatControl backGroundVolCtrl;
	static Float backGroundMutelvl = -40.0f;
	static Float backGroundNominal = -20.0f;
	static boolean backGroundPlaying;
	

	protected Playable playable;
	
	protected boolean updated;                 //true if there is an audio file to play
	protected boolean isOnline;                //true by default. Can be switched to false for shutdown
	protected boolean playing;                 //true while the audio is running
	
	protected float currentVolume;
	protected FloatControl volCtrl;
	protected Clip clip;
	
	protected long clipTime;
	
	
	@Override
	public void update(Observable obs, Object obj) {
		//is called when Observable (Knot/Edge) change their booleans indicating they're accessed by the main window.
	}
	
	public AudioObserver() {
		updated = false;
		isOnline = true;
		playing = false;
		currentVolume = -60.0f;
	}
	
	
	
	//Audio player with options for muting
	public synchronized void playAudio(File clipFile, boolean mute, float nominal, float mutelvl) {
			class AudioListener implements LineListener {
				private boolean done = false;
				
				@Override public synchronized void update(LineEvent event) {
					LineEvent.Type eventType = event.getType();
					if (eventType == LineEvent.Type.STOP || eventType == LineEvent.Type.CLOSE) {
						done = true;
						notifyAll();
					}
				}
				public synchronized void waitUntilDone() throws InterruptedException {
					while (!done) { 
						wait(); 
					}
				}
			}
					
			try {
				AudioListener listener = new AudioListener();
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
				clip = AudioSystem.getClip();
								
				//get audio controls
				clip.addLineListener(listener);
				clip.open(audioInputStream);
				volCtrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				//sets initial volume
				if (mute ) {
					volCtrl.setValue(mutelvl);
					currentVolume = mutelvl;
				} else {
					volCtrl.setValue(nominal);
					currentVolume = nominal;
				}
				//run the recording
				try {
					clip.start();
					playing = true;
					listener.waitUntilDone();
				}  finally {
					clip.close();
					playing = false;
				}
			  
				audioInputStream.close();
			} catch (IOException | UnsupportedAudioFileException
					| LineUnavailableException | InterruptedException e) {
				e.printStackTrace();
			} 
		  
			finally {
				//what happens after invocation of the player
				audioControl(playable);
			}
		  
	}
	
	//Pause function
	public void pause(boolean pause) {
		if (pause) {
			//Get time of clip
			clipTime= clip.getMicrosecondLength();
			clip.stop();
		} else {
			clip.setMicrosecondPosition(clipTime);
			clip.start();
		}
	}
	
	//Skip function
	public void skip(boolean skip) {
		
	}
	
	
	//options for shutdown or repeat running
	public void audioControl(Playable playable) {
		//insert command
	}
	
	//adjusts Volume smoothly
	public float setVolume(float volume, float currentVolume, FloatControl volumeControler) {
			float end = volume;
			float start = currentVolume;
			float newVolume;
			if (start != end) {
			int steps = 40;
			for (int i = 0; i<steps; i++) {
				newVolume = (float) ((end-start)/(1+Math.exp(0.3f*((steps/2)-i)))+start);     //Logistical adjustment
//				newVolume = (float) (end - (end-start)*Math.exp(-i*0.1f));                    //Exponential adjustment 
				try{
					Thread.sleep(25);
				} catch (InterruptedException ie){
					ie.printStackTrace();
				}
				volumeControler.setValue(newVolume);
				System.out.println("Volume (dB): "+newVolume+" "+Thread.currentThread());
			}
			volumeControler.setValue(end);
			System.out.println("abgeschlossen");
			}
			return end;
	}
	
	//stops Thread from repeating run(); TODO: abort playing sound
	public void shutdown() {
		isOnline = false;
	}
	
	//Method for testing audio files
	public void testAudio(String name) throws FileNotFoundException {
		File test1 = new File(foreGroundPath+name+".wav");
		File test2 = new File(backGroundPath+name+".wav");
		if (!test1.isFile() && !test2.isFile()) {
			System.out.println("Fehler in: "+name);
			throw new FileNotFoundException();
		}
	}
	
	//Method for testing whether or not there is a foreground
	public boolean hasBackGround(String name) {
		File test = new File(backGroundPath+name+".wav");
		if (!test.isFile()) {
			return false;
		} else {
			return true;
		}
	}
	//Method for testing whether or not there is a foreground
	public boolean hasForeGround(String name) {
		File test = new File(foreGroundPath+name+".wav");
		if (!test.isFile()) {
			return false;
		} else {
			return true;
		}
	}
		
	//Getters and Setters
	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	public FloatControl getVolCtrl() {
		return volCtrl;
	}

	public void setVolCtrl(FloatControl volCtrl) {
		this.volCtrl = volCtrl;
	}

}
