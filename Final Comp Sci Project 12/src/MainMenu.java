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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class MainMenu extends JFrame{
	private JFrame menu;
	MainMenu() {
		super("Main Menu");
	    this.menu = this; 
	    this.setSize(750,499);    
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    this.setResizable (false);
	    this.setUndecorated(true);
	    setBackground(new Color(0,0,0,0));
	    
	    //Create a MainMenuPanel
	    JPanel mainMenuPanel = new DecoratedPanel();
	    mainMenuPanel.setLayout(new BorderLayout());
	    mainMenuPanel.setBorder(new EmptyBorder(0, 0, 85, 0));
	    
	    //start button
	    ImageIcon explore =new ImageIcon("images/explore.png");
	    JButton startButton = new JButton(explore);
	    startButton.setBackground(new Color(0, 0, 0, 0));
	    startButton.setBorder(BorderFactory.createEmptyBorder());
	    startButton.setFocusPainted(false);
	    startButton.setRolloverIcon(new ImageIcon("images/blastoff.png"));
	    startButton.addActionListener(new StartButtonListener());
	    
	    //logo
	    BufferedImage logo = null;
		try {logo = ImageIO.read(new File("images/Solar Orbiter Logo.png"));} catch (IOException e) {}
	    JLabel logoLabel = new JLabel(new ImageIcon(logo));
	    
	    //quit button
	    ImageIcon quit =new ImageIcon("images/quit.png");
	    JButton quitButton = new JButton(quit);
	    quitButton.setBackground(new Color(0, 0, 0, 0));
	    quitButton.setBorder(BorderFactory.createEmptyBorder());
	    quitButton.setFocusPainted(false);
	    quitButton.addActionListener(new QuitButtonListener());
	    
	    mainMenuPanel.add (logoLabel, BorderLayout.NORTH);
	    mainMenuPanel.add(quitButton, BorderLayout.SOUTH);
	    mainMenuPanel.add(startButton,BorderLayout.CENTER);
	    
	    this.add(mainMenuPanel);	    
	    this.setVisible(true);
	}
	 //inner class
	 class StartButtonListener implements ActionListener{ 
	    public void actionPerformed(ActionEvent event) {  
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