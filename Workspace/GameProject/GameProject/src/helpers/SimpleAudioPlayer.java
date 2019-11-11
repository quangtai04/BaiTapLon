package helpers;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SimpleAudioPlayer {

	// to store current position
	Long currentFrame;
	public static Clip clip;

	AudioInputStream audioInputStream;
	public static String filePath;

	public SimpleAudioPlayer(String filePath) {
		this.filePath = filePath;
		// create AudioInputStream object
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
			// create clip reference
			clip.open(audioInputStream);
			// open audioInputStream to the clip
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();
	}

	// Method to pause the audio
	public void pause() {
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
	}
	public void continueMusic()	{
		clip.setMicrosecondPosition(currentFrame);
		clip.start();
	}
	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		clip.start();
	}

	// Method to stop the audio
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}