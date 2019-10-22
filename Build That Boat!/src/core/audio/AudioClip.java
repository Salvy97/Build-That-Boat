package core.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class AudioClip 
{
	private int IDClip;
	private AudioInputStream audioInputStream;
	private Clip audioClip;
	
	public AudioClip(int IDClip, AudioInputStream audioInputStream, Clip audioClip)
	{
		this.IDClip = IDClip;
		this.audioInputStream = audioInputStream;
		this.audioClip = audioClip;
	}
	
	public int getIDClip() { return IDClip; }
	public AudioInputStream getAudioInputStream() { return audioInputStream; }
	public Clip getAudioClip() { return audioClip; }
	
	public void setID(int IDClip) { this.IDClip  = IDClip; }
	public void setAudioClip(Clip audioClip) { this.audioClip = audioClip; }
	public void setAudioInputStream(AudioInputStream audioInputStream) { this.audioInputStream = audioInputStream; }
	
	public void playClip()
	{
	    if (!audioClip.isRunning())
	    {
		    audioClip.setFramePosition(0);
		    audioClip.start();
	    }
	}
}