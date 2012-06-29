package edu.propra.bomberman.audio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.applet.*;
import javax.sound.midi.*;

public class Jukebox {
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
			sounds = new AudioClip[2];
			sounds[0] = Applet.newAudioClip(loader
					.getResource("resources/gun.wav"));
			sounds[1] = Applet.newAudioClip(loader
					.getResource("resources/roll.wav"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void playSound(int index) {
		if (index > 0 && index < sounds.length)
			sounds[index].play();
	}

	public static void main(String[] args) {
		Jukebox jukebox = new Jukebox(); // initialize jukebox
		
		jukebox.playSound(0); // play first sound
		try {
			Thread.sleep(100); // wait a bit
		} catch (InterruptedException ex) {
		}
		jukebox.playSound(1); // play second sound
		
		
		
		Sequence music = jukebox.loadSequence("resources/Deadmau5_-_Arguru.mid"); // load Sequence http://www.nonstop2k.com/community/midifiles/3239-deadmau5-arguru-progressive-house-midi.html
		jukebox.playSequence(music); // start playing Sequence
		try {
			Thread.sleep(5000); // wait a bit
		} catch (InterruptedException ex) {
		}
		Sequence music2 = jukebox.loadSequence("resources/atb__don't_stop.mid"); // load Sequence http://www.nonstop2k.com/community/midifiles/980-atb-don-t-stop-trance-midi.html
		jukebox.playSequence(music2); // start playing Sequence
		
	}


	
	private void playSequence(Sequence seq) {
		// TODO Auto-generated method stub
		if (seq == null) return;
		try
		{
			
		// Create a sequencer for the sequence	
		Sequencer sequencer = MidiSystem.getSequencer();
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
}