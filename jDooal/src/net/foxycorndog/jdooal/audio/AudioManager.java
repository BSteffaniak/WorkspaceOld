package net.foxycorndog.jdooal.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.WaveData;

public class AudioManager
{
	static
	{
		try
		{
			AL.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	ArrayList<WaveData> audio;
	
	public AudioManager()
	{
		audio = new ArrayList<WaveData>();
	}
	
	public int addSound(String location)
	{
		WaveData sound = null;
		
		try
		{
			sound = WaveData.create(new BufferedInputStream(new FileInputStream(location)));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		audio.add(sound);
		
		int id = 0;
		id = alGenBuffers();
		
		alBufferData(id, sound.format, sound.data, sound.samplerate);
		
		//alDeleteBuffers(id);
		
		int source = alGenSources();
		
		alSourcei(source, AL_BUFFER, id);
		alSource3f(source, AL_POSITION, 0, 0, 0);
		alSource3f(source, AL_VELOCITY, 0, 0, 0);
		alSourcef(source, AL_GAIN, 1f);
		
		//alDeleteSources(source);
		
		return source;
	}
	
	public void play(int source)
	{
		alSourcePlay(source);
	}
	
	public void stop(int source)
	{
		alSourceStop(source);
	}
}