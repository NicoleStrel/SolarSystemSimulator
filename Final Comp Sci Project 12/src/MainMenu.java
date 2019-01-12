//imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class MainMenu extends JFrame{
	private JFrame menu;
	private Clip music;
	MainMenu() {
		super("Main Menu");
	    this.menu = this; 
	    this.setSize(750,499);    
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    this.setResizable (false);
	    this.setUndecorated(true);
	    setBackground(new Color(0,0,0,0));
	    
	    //------MainMenuPanel-----
	    JPanel mainMenuPanel = new DecoratedPanel();
	    mainMenuPanel.setLayout(new BorderLayout());
	    mainMenuPanel.setBorder(new EmptyBorder(0, 0, 85, 0));
	    
	    //-----start button------
	    ImageIcon explore =new ImageIcon("images/explore.png");          //make boundries for this
	    JButton startButton = new JButton(explore);
	    startButton.setBackground(new Color(0, 0, 0, 0));
	    startButton.setBorder(BorderFactory.createEmptyBorder());
	    startButton.setFocusPainted(false);
	    startButton.setRolloverIcon(new ImageIcon("images/blastoff.png"));
	    startButton.addActionListener(new StartButtonListener());
	    mainMenuPanel.add(startButton,BorderLayout.CENTER);
	    
	    //--------logo----------
	    BufferedImage logo = null;
		try {logo = ImageIO.read(new File("images/Solar Orbiter Logo.png"));} catch (IOException e) {}
	    JLabel logoLabel = new JLabel(new ImageIcon(logo));
	    mainMenuPanel.add (logoLabel, BorderLayout.NORTH);
	    
	    //--------quit button------
	    ImageIcon quit =new ImageIcon("images/quit.png");
	    JButton quitButton = new JButton(quit);
	    quitButton.setBackground(new Color(0, 0, 0, 0));
	    quitButton.setBorder(BorderFactory.createEmptyBorder());
	    quitButton.setFocusPainted(false);
	    quitButton.addActionListener(new QuitButtonListener());	   
	    mainMenuPanel.add(quitButton, BorderLayout.SOUTH);
        
	    //--------audio--------
	    File musicFile = new File("audio/intro.wav");
	    
	    try {
	    	AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
	    	music = AudioSystem.getClip();
	    	music.open(audioIn);
	    	music.loop(Clip.LOOP_CONTINUOUSLY);	    	
	    } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	         e.printStackTrace();
	    }	    
	    
	    this.add(mainMenuPanel);	    
	    this.setVisible(true);
	}
	 //inner class
	 class StartButtonListener implements ActionListener{ 
	    public void actionPerformed(ActionEvent event) {  
	      music.stop();
	      menu.dispose();
	      new Explore();

	    }

	  }
	 class QuitButtonListener implements ActionListener{
		 public void actionPerformed(ActionEvent event)  {  
			 menu.dispose();
			 System.exit(0);
		 }
	 }
	 private class DecoratedPanel extends JPanel {	
		 private BufferedImage logo;
		    DecoratedPanel() {
		      this.setBackground(new Color(0,0,0,0));		      
		    }
		    
		    public void paintComponent(Graphics g) { 
		        super.paintComponent(g);     
		        Image background = new ImageIcon("images/space.jpg" ).getImage();
		        g.drawImage(background,0,0,null); 
		   }		  
		   
	 }
}