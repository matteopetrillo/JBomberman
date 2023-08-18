package it.petrillo.jbomberman.controller;

import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.*;
import javax.swing.*;

import static it.petrillo.jbomberman.util.GameConstants.*;

/**
 * The AudioManager class handles audio playback for the game.
 */
public class AudioManager {

	private static AudioManager audioManagerInstance;
	private boolean muted;
	private final Clip menuMusic;
	private final Clip gameMusic;
	private final Clip walking;
	private Timer fadeOutTimer;

	private AudioManager() {
		walking = getAudioClip("/SFX/walking_sfx.wav");
		menuMusic = getAudioClip("/SFX/menu_music.wav");
		gameMusic = getAudioClip("/SFX/gaming_music.wav");
	}

	/**
	 * Plays the menu music.
	 */
	public void playMenuMusic() {
		setDBs(menuMusic,-10f);
		if (!muted)
			menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Fades out the menu music.
	 */
	public void fadeOutMenuMusic() {
		fadeOut(menuMusic);
	}

	/**
	 * Plays the gameplay music.
	 */
	public void playGameplayMusic() {
		FloatControl gainControl = (FloatControl) gameMusic.getControl(FloatControl.Type.MASTER_GAIN);
		if (!muted) {
			gainControl.setValue(gainControl.getMinimum());
			gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
			gainControl.shift(gainControl.getValue(), -18f, 5000);
		}
	}

	/**
	 * Fades out the gameplay music.
	 */
	public void fadeOutGamePlayMusic() {
		fadeOut(gameMusic);
	}

	/**
	 * Fades out the audio of the specified clip gradually until it stops.
	 *
	 * @param clip The audio clip to fade out.
	 */
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

	/**
	 * Plays the walking sound effect.
	 */
	public void playWalkingSFX() {
		setDBs(walking,-18f);
		if (!muted) {
			walking.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Stops the walking sound effect.
	 */
	public void stopWalkingSFX() {
		walking.stop();
	}

	/**
	 * Sets the volume level in decibels (dB) for the specified audio clip.
	 *
	 * @param clip      The audio clip for which to set the volume.
	 * @param dbVolume  The volume level in decibels to set.
	 */
	private void setDBs(Clip clip, float dbVolume) {
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(dbVolume);
	}

	/**
	 * Plays the specified audio file with the given volume.
	 *
	 * @param filename  The name of the audio file to be played.
	 * @param dbVolume  The volume in decibels to play the audio at.
	 */
	public void play(String filename, float dbVolume) {
		String path = Path.of(USER_BASE_DIR, filename).toString();
		Clip audioClip = getAudioClip(filename);
		if (audioClip != null) {
			setDBs(audioClip, dbVolume);
			if (!muted)
				audioClip.start();
		}
	}

	/**
	 * Retrieves a Clip object by loading an audio file from the specified path.
	 *
	 * @param path  The relative path to the audio file.
	 * @return      The loaded Clip object, or null if loading fails.
	 */
	public Clip getAudioClip(String path) {
			try (InputStream is = AudioManager.class.getResourceAsStream(path)) {
				if (is != null) {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(is);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					return clip;
				}
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				System.out.println("Impossibile caricare l'audio dal percorso: " + path);
				e.printStackTrace();
			}
			System.out.println("Risorsa audio al percorso: " + path + " non trovata.");
			return null;
		}

	/**
	 * Toggles the mute state.
	 */
	public void toggleMute() {
		muted = !muted;
	}

	/**
	 * Retrieves the AudioManager instance. If the instance doesn't exist,
	 * creates a new one.
	 *
	 * @return The AudioManager instance.
	 */
	public static AudioManager getAudioManagerInstance() {
		if (audioManagerInstance == null)
			audioManagerInstance = new AudioManager();
		return audioManagerInstance;
	}
}
