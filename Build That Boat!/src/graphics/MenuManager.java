package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import globals.FileManager;
import globals.Sprite;
import static globals.ConstantsCollection.SCREEN_W;
import static globals.ConstantsCollection.SCREEN_H;

@SuppressWarnings("serial")
public class MenuManager extends JPanel
{
	public static final int DIFFICULTY_INDEX = 0;
	public static final int AUDIO_MUTE_INDEX = 1;
	public static final int RESOLUTION_W_INDEX = 2;
	public static final int RESOLUTION_H_INDEX = 3;
	
	private Color color;
	
	Object[] gameInfos;
	private boolean startGame;
	private boolean optionsMode;
	private Sprite background;
	
	private final int buttonWidth = SCREEN_W / 5;
	private final int buttonHeight = SCREEN_H / 5;
	
	private JButton playButton;
	private JButton optionsButton;
	private JButton exitButton;
	private JButton backButton;
	
	private final String[] difficulties = new String[] { "Easy", "Medium", "Hard" };
	private JComboBox<String> difficultyChoices;
	
	private JCheckBox muteAudio;
	
	private JTextField resolutionW;
	private JTextField resolutionH;
	
	private AudioManager audioManager;
	
	public MenuManager()
	{
		gameInfos = new Object[4];
		background = new Sprite("sprites/background.png", SCREEN_W, SCREEN_H);
		color = Color.RED;
		
		gameInfos[DIFFICULTY_INDEX] = 2;
		gameInfos[AUDIO_MUTE_INDEX] = false;
		gameInfos[RESOLUTION_W_INDEX] = SCREEN_W;
		gameInfos[RESOLUTION_H_INDEX] = SCREEN_H;	
		
		startGame = false;
		optionsMode = false;
		
		try
		{
			BufferedImage buttonIcon = ImageIO.read(new File("sprites/playButton.png"));
			playButton = new JButton(new ImageIcon(buttonIcon));
			playButton.setBorder(BorderFactory.createEmptyBorder());
			playButton.setContentAreaFilled(false);
		}
		catch (IOException e) 
	    {
			e.printStackTrace();
	    }
		try
		{
			BufferedImage buttonIcon = ImageIO.read(new File("sprites/optionsButton.png"));
			optionsButton = new JButton(new ImageIcon(buttonIcon));
			optionsButton.setBorder(BorderFactory.createEmptyBorder());
			optionsButton.setContentAreaFilled(false);
		}
		catch (IOException e) 
	    {
			e.printStackTrace();
	    }
		try
		{
			BufferedImage buttonIcon = ImageIO.read(new File("sprites/exitButton.png"));
			exitButton = new JButton(new ImageIcon(buttonIcon));
			exitButton.setBorder(BorderFactory.createEmptyBorder());
			exitButton.setContentAreaFilled(false);
		}
		catch (IOException e) 
	    {
			e.printStackTrace();
	    }
		try
		{
			BufferedImage buttonIcon = ImageIO.read(new File("sprites/backButton.png"));
			backButton = new JButton(new ImageIcon(buttonIcon));
			backButton.setBorder(BorderFactory.createEmptyBorder());
			backButton.setContentAreaFilled(false);
		}
		catch (IOException e) 
	    {
			e.printStackTrace();
	    }
		
		difficultyChoices = new JComboBox<String>(difficulties);
		muteAudio = new JCheckBox("Mute Audio");
		resolutionW = new JTextField(5);
		resolutionH = new JTextField(5);

		add(playButton);
		add(optionsButton);
		add(exitButton);
		add(backButton);
		add(difficultyChoices);
		add(muteAudio);
		add(resolutionW);
		add(resolutionH);
		
		playButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				audioManager.stopAudio(AudioIDCollector.MENU_ID);
				startGame = true;
			}
		});
		optionsButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				optionsMode = true;
				repaint();
			}
		});
		exitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				optionsMode = false;
				switch ((String)difficultyChoices.getSelectedItem())
				{
					case "Easy":	gameInfos[DIFFICULTY_INDEX] = 1;
									break;
					case "Medium":	gameInfos[DIFFICULTY_INDEX] = 2;
									break;
					case "Hard":	gameInfos[DIFFICULTY_INDEX] = 3;
									break;
				}
				if (muteAudio.isSelected())
				{
					gameInfos[AUDIO_MUTE_INDEX] = true;
					audioManager.stopAudio(AudioIDCollector.MENU_ID);
					color = Color.BLUE;
				}
				else  
				{ 
					gameInfos[AUDIO_MUTE_INDEX] = false;
					audioManager.playAudio(AudioIDCollector.MENU_ID);
				}
				if (!resolutionW.getText().isEmpty() && Integer.parseInt(resolutionW.getText()) >= 800 && Integer.parseInt(resolutionW.getText()) <= 1920)
				{
					if (!resolutionW.getText().isEmpty() && Integer.parseInt(resolutionH.getText()) >= 600 && Integer.parseInt(resolutionH.getText()) <= 1080)
					{
						gameInfos[RESOLUTION_W_INDEX] = Integer.parseInt(resolutionW.getText());
						gameInfos[RESOLUTION_H_INDEX] = Integer.parseInt(resolutionH.getText());
					}
				}
				repaint();
			}
		});
		
		audioManager = new AudioManager(false);
		audioManager.playAudio(AudioIDCollector.MENU_ID);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(background.getSprite(), 0, 0, background.getWidth(), background.getHeight(), null);

		if (!optionsMode)
		{
			g.setFont(new Font("Microdot", Font.BOLD, SCREEN_W / 20));
			g.setColor(color);
			g.drawString("Build That Boat!", SCREEN_W / 2 - SCREEN_W / 3, SCREEN_H / 2 - buttonWidth);
			g.setFont(new Font("Arial", Font.BOLD, SCREEN_W / 60));
			g.drawString("High Score: " + FileManager.content("highscore.txt").get(0), SCREEN_W - 300, SCREEN_H - 50);
			
			playButton.setVisible(true);
			playButton.setEnabled(true);
			playButton.setLocation(SCREEN_W / 2 - buttonWidth / 2, SCREEN_H / 2 - buttonWidth / 2);
			playButton.setSize(buttonWidth, buttonHeight);
			
			optionsButton.setVisible(true);
			optionsButton.setEnabled(true);
			optionsButton.setLocation(SCREEN_W / 2 - buttonWidth / 2, SCREEN_H / 2);
			optionsButton.setSize(buttonWidth, buttonHeight);
			
			exitButton.setVisible(true);
			exitButton.setEnabled(true);
			exitButton.setLocation(SCREEN_W / 2 - buttonWidth / 2, SCREEN_H / 2 + buttonWidth / 2);
			exitButton.setSize(buttonWidth, buttonHeight);
			
			backButton.setVisible(false);
			backButton.setEnabled(false);
			difficultyChoices.setEnabled(false);
			difficultyChoices.setVisible(false);
			muteAudio.setEnabled(false);
			muteAudio.setVisible(false);
			resolutionW.setEnabled(false);
			resolutionW.setVisible(false);
			resolutionH.setEnabled(false);
			resolutionH.setVisible(false);
		}
		else
		{
			playButton.setVisible(false);
			playButton.setEnabled(false);
			optionsButton.setVisible(false);
			optionsButton.setEnabled(false);
			exitButton.setVisible(false);
			exitButton.setEnabled(false);
			
			backButton.setVisible(true);
			backButton.setEnabled(true);
			backButton.setLocation(SCREEN_W / 19, SCREEN_H - SCREEN_H / 4);
			backButton.setSize(buttonWidth, buttonHeight);
			
			difficultyChoices.setEnabled(true);
			difficultyChoices.setVisible(true);
			g.setFont(new Font("Times New Roman", Font.BOLD, SCREEN_W / 30));
			g.setColor(Color.RED);
			g.drawString("Difficulty:", SCREEN_W / 3, SCREEN_H / 2 - buttonWidth);
			difficultyChoices.setLocation(SCREEN_W / 2, SCREEN_H / 10);	
			difficultyChoices.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 60));
			
			muteAudio.setEnabled(true);
			muteAudio.setVisible(true);
			muteAudio.setLocation(SCREEN_W / 2 - buttonWidth / 4, SCREEN_H / 4);
			muteAudio.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 30));
			muteAudio.setBorder(BorderFactory.createEmptyBorder());
			muteAudio.setContentAreaFilled(false);
			
			resolutionW.setEnabled(true);
			resolutionW.setVisible(true);
			resolutionW.setLocation(SCREEN_W / 2, SCREEN_H / 3 + SCREEN_H / 200);
			resolutionW.setSize(SCREEN_W / 15, SCREEN_H / 22);
			g.drawString("Width:", SCREEN_W / 3, SCREEN_H / 3 + SCREEN_H / 27);
			resolutionH.setEnabled(true);
			resolutionH.setVisible(true);
			resolutionH.setLocation(SCREEN_W / 2, SCREEN_H / 2 - SCREEN_H / 18);
			resolutionH.setSize(SCREEN_W / 15, SCREEN_H / 22);
			g.drawString("Height:", SCREEN_W / 3, SCREEN_H / 2 - SCREEN_H / 60);
		}
	}
	
	public boolean isGameStarted() { return startGame; }
	public Object[] getGameLogicInfos() 
	{ 
		Object[] gameLogicInfos = { gameInfos[DIFFICULTY_INDEX], gameInfos[AUDIO_MUTE_INDEX] };
		return gameLogicInfos; 
	}
	public Object[] getResolutionInfos() 
	{ 
		Object[] resolutionInfos = { gameInfos[RESOLUTION_W_INDEX], gameInfos[RESOLUTION_H_INDEX] };
		return resolutionInfos; 
	}
}