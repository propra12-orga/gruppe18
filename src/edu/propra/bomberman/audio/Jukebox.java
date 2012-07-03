package edu.propra.bomberman.audio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.applet.*;
import javax.sound.midi.*;

public class Jukebox {
	public static final int Gun=0;
	public static final int Roll=1;
	public static final int Explosion=2;
	public static final int Soundup=3;
	public static final int Sounddown=4;
	public static final int Death=5;
	private AudioClip[] sounds; // AudioClips

	public Jukebox() {
		initSounds();
	}

	public Sequence loadSequence(String filename) {
		Sequence result = null;
		try {
			ClassLoader loader = Jukebox.class.getClassLoader();
			URL url = loader.getResource(filename);
			result = MidiSystem.getSequence(url);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void initSounds() {
		try {
			// needed for correct loading of resources in JAR-Files
			ClassLoader loader = Jukebox.class.getClassLoader();
			// load AudioClips
			sounds = new AudioClip[7];
			sounds[Jukebox.Gun] = Applet.newAudioClip(loader
					.getResource("resources/gun.wav"));
			sounds[Jukebox.Roll] = Applet.newAudioClip(loader
					.getResource("resources/roll.wav"));
			sounds[Jukebox.Explosion] = Applet.newAudioClip(loader
					.getResource("resources/explosion.wav"));
			/*sounds[Jukebox.Soundup] = Applet.newAudioClip(loader
					.getResource("resources/sound_up.wav"));
			sounds[Jukebox.Sounddown] = Applet.newAudioClip(loader
					.getResource("resources/sound_down.wav"));
			sounds[Jukebox.Death] = Applet.newAudioClip(loader
					.getResource("resources/death.wav"));
			*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playSound(int index) {
		//if (index > 0 && index < sounds.length)
		//	sounds[index].play();
	}

	public static void main(String[] args) {
		Jukebox jukebox = new Jukebox(); // initialize jukebox
/*		
		jukebox.playSound(0); // play first sound
		try {
			Thread.sleep(100); // wait a bit
		} catch (InterruptedException ex) {
		}
		jukebox.playSound(1); // play second sound
		
		
		
		Sequence music = jukebox.loadSequence("resources/Deadmau5_-_Arguru.mid"); // load Sequence http://www.nonstop2k.com/community/midifiles/3239-deadmau5-arguru-progressive-house-midi.html
		jukebox.playSequence(music); // start playing Sequence
		try {
		
			
			Thread.sleep(1000); // wait a bit
			
		} catch (InterruptedException ex) {
		}
		jukebox.stopSequence();
		
		
		
		Sequence music2 = jukebox.loadSequence("resources/atb__don't_stop.mid"); // load Sequence http://www.nonstop2k.com/community/midifiles/980-atb-don-t-stop-trance-midi.html
		jukebox.playSequence(music2); // start playing Sequence
*/		
		
		jukebox.playSound(5);
		jukebox.playSound(5);
		jukebox.playSound(5);
		jukebox.playSound(5);
		jukebox.playSound(5);
try {
		
			
			Thread.sleep(1000); // wait a bit
			
		} catch (InterruptedException ex) {
		}
	}


	Sequencer sequencer; 
	private void playSequence(Sequence seq) {
		// TODO Auto-generated method stub
		if (seq == null) return;
		try
		{
			
		// Create a sequencer for the sequence	
		sequencer = MidiSystem.getSequencer();
	    sequencer.open();
		sequencer.setSequence(seq);
		
		// Start playing
		sequencer.start();
		}
		catch (MidiUnavailableException e) {
	    } 
		catch (InvalidMidiDataException ex)
		{
		ex.printStackTrace();
		}
	}
	private void stopSequence(){
		if(sequencer.isRunning())sequencer.stop();
	}
}