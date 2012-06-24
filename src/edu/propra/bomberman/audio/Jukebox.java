package edu.propra.bomberman.audio;

import java.net.URL;
import java.applet.*;

public class Jukebox {
	private AudioClip[] sounds; // AudioClips

	public Jukebox() {
		initSounds();
	}

	private void initSounds() {
		try {
			// needed for correct loading of resources in JAR-Files
			ClassLoader loader = Jukebox.class.getClassLoader();
			// load AudioClips
			sounds = new AudioClip[2];
			sounds[0] = Applet.newAudioClip(loader.getResource("resources/gun.wav"));
			sounds[1] = Applet.newAudioClip(loader.getResource("resources/roll.wav"));
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
	}
}