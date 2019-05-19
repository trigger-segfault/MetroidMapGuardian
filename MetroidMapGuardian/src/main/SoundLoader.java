package main;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.*;


public class SoundLoader {
	
	private static HashMap<String, Sound> soundMap = new HashMap<String, Sound>();
	
	private static int soundsLoaded = 0;
	
	// Songs
	
	
	
	public static void loadSounds() {
		
		System.out.println("Loading Music:");

		soundsLoaded = 0;
		
		// Load Songs

		
		System.out.println("");
		System.out.println("Loaded " + String.valueOf(soundsLoaded) + " songs");
		System.out.println("--------------------------------");
		System.out.println("Loading Sounds:");
		
		soundsLoaded = 0;
		
		// Load Sounds
		loadSound("activation", "activation.wav", 4, 0.0f, 0);
		loadSound("focus_change", "focus_change.wav", 4, 0.0f, 0);
		loadSound("marker_activation", "activation_change_fail.wav", 4, 0.0f, 0);
		loadSound("marker_focus_change", "focus_change_fail.wav", 4, 0.0f, 0);
		
		System.out.println("");
		System.out.println("Loaded " + String.valueOf(soundsLoaded) + " sounds");
		System.out.println("--------------------------------");
		
		Sound.setVolumeAll(0.0f);
	}
	
	public static Sound getSound(String name) {
		return soundMap.get(name);
	}
	
	public static Clip loadClip(String path) {
		try {
			URL url = SoundLoader.class.getResource(path);
			
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			//new JS_MP3ConversionProvider();
			//new JS_MP3FileReader();
			//new JavaDecoder();
			return clip;
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
	    	e.printStackTrace();
	    }
		catch (LineUnavailableException e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
	public static void loadSound(String name, String path, int channels, float volumeOffset, int group) {

		System.out.println("- " + path);

		soundsLoaded += 1;
		Sound sound = new Sound(group);
		for( int i = 0; i < channels; i++ ) {
			Clip clip = loadClip("/resources/sounds/" + path);
			if( clip != null )
				sound.add(clip);
			else
				return;
		}
		sound.setVolume(volumeOffset);
		Sound.sounds.add(sound);
		soundMap.put(name, sound);
	}

}
