package net.foxycorndog.presto2d.audio;

//import java.io.FileInputStream;
//import java.io.IOException;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedAudioFileException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class NoiseClip
{
	public String location = null;
	
	public static int volume = 100;

	// Each sound effect has its own clip, loaded with its own sound file.
	private AudioClip clip;
	
//	private int frm = -1;

	// Constructor to construct each element of the enum with its own sound file.
	public NoiseClip(String soundFileName)
	{
		location = soundFileName;
		
//		try
//		{
			//clip = Applet.newAudioClip(NoiseClip.class.getResource(soundFileName));
		try
		{
			clip = Applet.newAudioClip(new File(soundFileName).toURI().toURL());
		}
		catch (MalformedURLException ex)
		{
			
		}
			
//			// Set up an audio input stream piped from the sound file.
//			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new FileInputStream(soundFileName));
//			// Get a clip resource.
//			clip = AudioSystem.getClip();
//			// Open audio clip and load samples from the audio input stream.
//			clip.open(audioInputStream);
//		}
//		catch (UnsupportedAudioFileException e)
//		{
//			System.out.println(e);
//		}
//		catch (IOException e)
//		{
//			System.out.println(e);
//		}
//		catch (LineUnavailableException e)
//		{
//			System.out.println(e);
//		}
	}

//	// Play or Re-play the sound effect from the beginning, by rewinding.
//	public void play()
//	{
//		if (volume > 0)
//		{
//			if (!clip.isRunning())
//			{
//				stop();
//			}
//			// Start playing the clip
//			clip.start();
//		}
//	}

	public void play()
	{
		try
		{
			new Thread()
			{
				@Override
				public void run()
				{
					clip.play();
				}
			}.start();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public void loop()
	{
		try
		{
			new Thread()
			{
				@Override
				public void run()
				{
					clip.loop();
				}
			}.start();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		try
		{
			new Thread()
			{
				@Override
				public void run()
				{
					clip.stop();
				}
			}.start();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

//	public void end()
//	{
//		clip.setFramePosition(clip.getFrameLength());
//	}
//	
//	public void stop()
//	{
//		// Stop the player if it is still running
//		clip.stop();
//		// rewind to the beginning
//		clip.setFramePosition(0);
//	}
//	
//	public void pause()
//	{
//		if (frm == -1)
//		{
//			frm = clip.getFramePosition();
//			stop();
//		}
//	}
//	
//	public void unpause()
//	{
//		if (frm > -1)
//		{
//			clip.setFramePosition(frm);
//			clip.start();
//		}
//	}
}