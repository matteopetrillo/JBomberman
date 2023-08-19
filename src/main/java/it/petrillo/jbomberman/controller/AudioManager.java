package it.petrillo.jbomberman.controller;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * The AudioManager class handles audio playback for the game.
 */
public class AudioManager {

	private static AudioManager audioManagerInstance;
	private boolean muted;
	private final Clip menuMusic;
	private final Clip gameMusic;
	private final Clip walkingSFX;
	private Timer fadeOutTimer;

	private AudioManager() {
		walkingSFX = getAudioClip("/SFX/walking_sfx.wav");
		menuMusic = getAudioClip("/SFX/menu_music.wav");
		gameMusic = getAudioClip("/SFX/gaming_music.wav");
	}

	/**
	 * Plays the menu music.
	 */
	public void playMenuMusic() {
		setDbs(menuMusic,-10f);
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
	 * Plays the walkingSFX sound effect.
	 */
	public void playWalkingSFX() {
		setDbs(walkingSFX,-18f);
		if (!muted) {
			walkingSFX.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Stops the walkingSFX sound effect.
	 */
	public void stopWalkingSFX() {
		walkingSFX.stop();
	}

	/**
	 * Sets the volume level in decibels (dB) for the specified audio clip.
	 *
	 * @param clip      The audio clip for which to set the volume.
	 * @param dBvolume  The volume level in decibels to set.
	 */
	private void setDbs(Clip clip, float dBvolume) {
		FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(dBvolume);
	}

	/**
	 * Plays the specified audio file with the given volume.
	 *
	 * @param filePath  The name of the audio file to be played.
	 * @param dBvolume  The volume in decibels to play the audio at.
	 */
	public void play(String filePath, float dBvolume) {
		Clip audioClip = getAudioClip(filePath);
		if (audioClip != null) {
			setDbs(audioClip, dBvolume);
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
	private Clip getAudioClip(String path) {
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
