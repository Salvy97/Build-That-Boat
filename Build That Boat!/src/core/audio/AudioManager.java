package core.audio;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioManager 
{
	private ArrayList<AudioClip> clips;
	private boolean isAudioMuted;
	
	public static String getAudioPath(int ID) { return "sounds/" + Integer.toString(ID) + ".wav"; }

	public AudioManager(boolean isAudioMuted) 
	{ 
		clips = new ArrayList<AudioClip>();
		this.isAudioMuted = isAudioMuted;
	}
	
	public boolean isIDPresent(int ID) 
	{ 
		boolean isIDPresent = false;
		for (int i = 0; i < clips.size(); i++)
			if (clips.get(i).getIDClip() == ID)
				isIDPresent = true;
		return isIDPresent;
	}
	
	public int getPosition(int ID)
	{
		for (int i = 0; i < clips.size(); i++)
			if (clips.get(i).getIDClip() == ID)
				return i;
		return -1;
	}
	
	public void playAudio(int ID)
	{
		if (!isAudioMuted)
		{
			if (ID < AudioIDCollector.CLIPS_NUMBER && ID >= 0 && !isIDPresent(ID))
			{
				try
				{
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getAudioPath(ID)).getAbsoluteFile()); 
					Clip audioClip = AudioSystem.getClip(); 
					clips.add(new AudioClip(ID, audioInputStream, audioClip));
					clips.get(getPosition(ID)).getAudioClip().open(audioInputStream);
					clips.get(getPosition(ID)).playClip();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (ID < AudioIDCollector.CLIPS_NUMBER && ID >= 0 && isIDPresent(ID)) 
			{
				clips.get(getPosition(ID)).playClip();	
			}
		}
	}
	
	public void playAudioOnce(int ID)
	{
		if (!isAudioMuted)
		{
			if (ID < AudioIDCollector.CLIPS_NUMBER && ID >= 0)
			{
				try
				{
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getAudioPath(ID))); 
					Clip audioClip = AudioSystem.getClip(); 
					clips.add(new AudioClip(ID, audioInputStream, audioClip));
					clips.get(getPosition(ID)).getAudioClip().open(audioInputStream);
					clips.get(getPosition(ID)).getAudioClip().start();
					clips.remove(getPosition(ID));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void playAudioEveryXSeconds(int ID, Timer timer, int seconds)
	{
		if (!isAudioMuted)
		{
			timer.scheduleAtFixedRate(new TimerTask()
			{
		        public void run() 
		        {
		        	try
					{
			        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getAudioPath(ID))); 
						Clip audioClip = AudioSystem.getClip(); 
						audioClip.open(audioInputStream);
			            audioClip.start();
					}
		            catch (Exception e)
					{
						e.printStackTrace();
					}
		        }
		    }, 4000, seconds * 1000);
		}
	}
	
	public void stopAudio(int ID) 
	{ 
		if (ID < AudioIDCollector.CLIPS_NUMBER && ID >= 0 && isIDPresent(ID))
			for (int i = 0; i < clips.size(); i++)
				if (clips.get(i).getIDClip() == ID)
					clips.get(i).getAudioClip().stop();
	}
	
	public void resetAudio()
	{
		for (int i = 0; i < clips.size(); i++)
		{
			clips.get(i).getAudioClip().stop();
			clips.remove(i);
		}
	}
}