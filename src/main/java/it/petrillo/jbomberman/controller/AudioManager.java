package it.petrillo.jbomberman.controller;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.sampled.*;
import javax.swing.*;

import static it.petrillo.jbomberman.util.GameConstants.*;

public class AudioManager {

	private static AudioManager audioManagerInstance;
	private boolean isMuted;
	private final Clip menuMusic;
	private final Clip gameMusic;
	private final Clip walking;
	private Timer fadeOutTimer;

	private AudioManager() {
		walking = getClipFromPath("/src/main/resources/walking_sfx.wav");
		menuMusic = getClipFromPath("/src/main/resources/menu_music.wav");
		gameMusic = getClipFromPath("/src/main/resources/gaming_music.wav");
	}

	public void playMenuMusic() {
		setDBs(menuMusic,-10f);
		if (!isMuted)
			menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void fadeOutMenuMusic() {
		fadeOut(menuMusic);
	}

	public void playGameplayMusic() {
		FloatControl gainControl = (FloatControl) gameMusic.getControl(FloatControl.Type.MASTER_GAIN);
		if (!isMuted) {
			gainControl.setValue(gainControl.getMinimum());
			gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
			gainControl.shift(gainControl.getValue(), -18f, 5000);
		}
	}

	public void fadeOutGamePlayMusic() {
		fadeOut(gameMusic);
	}

	private void fadeOut(Clip clip) {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		fadeOutTimer = new Timer(4, e -> {
			if (gainControl.getValue() == gainControl.getMinimum()) {
				clip.stop();
				fadeOutTimer.stop();
			}
			else {
				gainControl.setValue(gainControl.getValue()-1f);
			}
		});
		fadeOutTimer.start();
	}

	public void playWalkingSFX() {
		setDBs(walking,-18f);
		if (!isMuted) {
			walking.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void stopWalkingSFX() {
		walking.stop();
	}


	private void setDBs(Clip clip, float dbVolume) {
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(dbVolume);
	}

	public void play(String filename, float dbVolume) {
		String path = Path.of(USER_BASE_DIR,filename).toString();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(dbVolume);
			if (!isMuted)
				clip.start();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}

	public Clip getClipFromPath(String path) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(Paths.get(USER_BASE_DIR,
					path).toString()));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			return clip;
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void toggleMute() {
		isMuted = !isMuted;
	}

	public static AudioManager getAudioManagerInstance() {
		if (audioManagerInstance == null)
			audioManagerInstance = new AudioManager();
		return audioManagerInstance;
	}
}
