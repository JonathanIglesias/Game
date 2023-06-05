package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sound {

	Clip clip;
	URL soundURL[] = new URL[30];

	public sound() {
		soundURL[0] = getClass().getResource("/sound/Across the Sunvalley.wav");
		soundURL[1] = getClass().getResource("/sound/collect.wav");
		soundURL[2] = getClass().getResource("/sound/door.wav");
		soundURL[3] = getClass().getResource("/sound/Ian Martyn 8.wav");
		soundURL[4] = getClass().getResource("/sound/select.wav");
		soundURL[5] = getClass().getResource("/sound/Field-old.wav");
		soundURL[6] = getClass().getResource("/sound/Epilogue_loop.wav");
		soundURL[7] = getClass().getResource("/sound/Battle-madaminu.wav");
	}

	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {

		}
	}

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

}
