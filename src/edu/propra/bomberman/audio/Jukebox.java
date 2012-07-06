// Author Alex
package edu.propra.bomberman.audio;

import java.net.URL;
import java.applet.*;
import javax.sound.midi.*;

public class Jukebox {
	public static final int	Death		= 5;
	public static final int	Explosion	= 2;
	public static final int	Gun			= 0;
	public static final int	Roll		= 1;
	public static final int	Sounddown	= 4;
	public static final int	Soundup		= 3;
	Sequencer				sequencer;

	private AudioClip[]		sounds;			// AudioClips

	public Jukebox() {
		initSounds();
	}

	private void initSounds() {
		try {
			// needed for correct loading of resources in JAR-Files
			ClassLoader loader = Jukebox.class.getClassLoader();
			// load AudioClips
			sounds = new AudioClip[7];
			sounds[Jukebox.Gun] = Applet.newAudioClip(loader.getResource("resources/gun.wav"));
			sounds[Jukebox.Roll] = Applet.newAudioClip(loader.getResource("resources/roll.wav"));
			sounds[Jukebox.Explosion] = Applet.newAudioClip(loader.getResource("resources/explosion.wav"));
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

	private void playSequence(Sequence seq) {
		// TODO Auto-generated method stub
		if (seq == null) return;
		try {

			// Create a sequencer for the sequence	
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(seq);

			// Start playing
			sequencer.start();
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException ex) {
			ex.printStackTrace();
		}
	}

	public void playSound(int index) {
		if (index > 0 && index < sounds.length) sounds[index].play();
	}

	private void stopSequence() {
		if (sequencer.isRunning()) sequencer.stop();
	}
}