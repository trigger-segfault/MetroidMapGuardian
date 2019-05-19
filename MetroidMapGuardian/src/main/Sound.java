package main;
import java.util.ArrayList;
import javax.sound.sampled.*;


public class Sound {

	public static final int SOUND_GROUPS	= 2;
	
	public static final int groupRoom	= 1;
	public static final int groupMenu	= 2;
	
	
	// Sound Management
	public static ArrayList<Sound> sounds	= new ArrayList<Sound>();
	public static boolean		   muted	= false;
	public static float			   volume	= 0.0f;
	
	// Sound Data
	public ArrayList<Clip>	clips			= new ArrayList<Clip>();
	public int				currentSound	= 0;
	public float			volumeOffset	= 0.0f;
	public int				group			= 0;
	
	// Constructor
	public Sound(Clip clip, int group) {
		this.clips.add(clip);
		this.group = group;
	}

	public Sound(int group) {
		this.group = group;
	}
	public Sound() {
		
	}
	
	/*
	 *	Local Sound Functions
	 */
	
	public void play() {
		if( !muted ) {
			clips.get(currentSound).stop();
			clips.get(currentSound).setFramePosition(0);
			clips.get(currentSound).start();
			currentSound += 1;
			if( currentSound >= clips.size() ) {
				currentSound = 0;
			}
		}
	}

	public void stop() {
		for( int i = 0; i < clips.size(); i++ ) {
			clips.get(i).stop();
			clips.get(i).setFramePosition(0);
			
		}
	}
	
	public void pause() {
		for( int i = 0; i < clips.size(); i++ ) {
			if( clips.get(i).getMicrosecondPosition() > 0 && clips.get(i).isActive() ) {
				clips.get(i).stop();
			}
		}
	}
	
	public void resume() {
		for( int i = 0; i < clips.size(); i++ ) {
			if( clips.get(i).getMicrosecondPosition() > 0 && !clips.get(i).isActive() ) {
				clips.get(i).start();
			}
		}
	}
	
	public void setVolume(float volume) {
		volumeOffset = Math.max(-80.0f, Math.min(6.0f, volume));
		for( int i = 0; i < clips.size(); i++ ) {
			FloatControl ctrl = (FloatControl)clips.get(i).getControl(FloatControl.Type.MASTER_GAIN);
			ctrl.setValue(Math.max(-80.0f, Math.min(6.0f, Sound.volume + volumeOffset)));
		}
	}
	
	public boolean isPlaying() {
		for( int i = 0; i < clips.size(); i++ ) {
			if( clips.get(i).isActive() ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPaused() {
		for( int i = 0; i < clips.size(); i++ ) {
			if( clips.get(i).getMicrosecondPosition() > 0 && !clips.get(i).isActive() ) {
				return true;
			}
		}
		return false;
	}
	
	public int channels() {
		return clips.size();
	}
	
	public void add(Clip clip) {
		clips.add(clip);
	}
	
	public void remove() {
		clips.remove(clips.size() - 1);
		if( currentSound >= clips.size() ) {
			currentSound = 0;
		}
	}
	
	/*
	 *	Static Sound Functions
	 */
	
	public static void mute(boolean mute) {
		muted = mute;
		for( int i = 0; i < sounds.size(); i++ ) {
			sounds.get(i).setVolume(muted ? -80.0f : volume);
		}
	}
	
	public static void stopAll() {
		for( int i = 0; i < sounds.size(); i++ ) {
			sounds.get(i).stop();
		}
	}
	
	public static void stopAll(int group) {
		for( int i = 0; i < sounds.size(); i++ ) {
			if( sounds.get(i).group == group || group == 0 ) {
				sounds.get(i).stop();
			}
		}
	}
	
	public static void pauseAll() {
		for( int i = 0; i < sounds.size(); i++ ) {
			sounds.get(i).pause();
		}
	}
	
	public static void pauseAll(int group) {
		for( int i = 0; i < sounds.size(); i++ ) {
			if( sounds.get(i).group == group || group == 0 ) {
				sounds.get(i).pause();
			}
		}
	}
	
	public static void resumeAll() {
		for( int i = 0; i < sounds.size(); i++ ) {
			sounds.get(i).resume();
		}
	}

	public static void resumeAll(int group) {
		for( int i = 0; i < sounds.size(); i++ ) {
			if( sounds.get(i).group == group || group == 0 ) {
				sounds.get(i).resume();
			}
		}
	}
	
	public static void setVolumeAll(float volume) {
		Sound.volume = Math.max(-80.0f, Math.min(6.0f, volume));
		
		for( int i = 0; i < sounds.size(); i++ ) {
			sounds.get(i).setVolume(sounds.get(i).volumeOffset);
		}
	}
}
