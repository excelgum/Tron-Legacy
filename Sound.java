//Disclaimer: This Code is not Mine, it is used only for the enhancement of the game.
//the soundtrack is taken from Tron Legacy the Movie and is used for educational purposes only

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
	private Clip song;
	
	public Sound(String path)
	{
		try
		{
	         URL url = this.getClass().getClassLoader().getResource(path); // Open an audio input stream.
	         AudioInputStream audio_in = AudioSystem.getAudioInputStream(url);
	         song = AudioSystem.getClip(); // Get a sound clip resource.
	         song.open(audio_in); // Open audio clip and load samples from the audio input stream.
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		song.loop(0); // Play once
		this.reset();
	}
		
	public void loop()
	{
		song.loop(Clip.LOOP_CONTINUOUSLY); // Play continuously
	}
	
	public void stop()
	{
		song.stop(); // Stop
		this.reset(); 
	}
	
	private void reset()
	{
		if(song.isRunning() == false)
		{
			song.setFramePosition(0);
		}
	}
}
