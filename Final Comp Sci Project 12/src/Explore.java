import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class Explore extends JFrame{
    private static SolarSystem solarSystem;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<SpaceObject> spaceObjects;
    
	Explore(){
		super("Explore");  
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
	    this.setUndecorated(true);  
	    this.setResizable(false);
		    
	    //Set up frame 
	    generateSpaceObjects();
	    solarSystem = new SolarSystem(screenSize);
	    this.add(solarSystem);
	    
	    //-------control bar------
	    JPanel controlBar = new JPanel();
	    controlBar.setPreferredSize(new Dimension(300,(int)screenSize.getHeight()));
	    controlBar.setMinimumSize(controlBar.getPreferredSize());
	    controlBar.setMaximumSize(controlBar.getPreferredSize());
	    //controlBar.setBorder(new EmptyBorder(200, 20, 300, 50));
	    controlBar.setBackground(new Color(255,255,255));
	    controlBar.setLayout(new BoxLayout(controlBar,  BoxLayout.Y_AXIS));

	    //control label
	    BufferedImage controls = null;
		try {controls = ImageIO.read(new File("images/controls.png"));} catch (IOException e) {}
	    JLabel controlLabel = new JLabel(new ImageIcon(controls));
	    controlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    controlBar.add(controlLabel);
	    
	    //scroll bars
	    controlBar.add(new JScrollBar(JScrollBar.HORIZONTAL), Component.CENTER_ALIGNMENT);
	    controlBar.add(new JScrollBar(JScrollBar.HORIZONTAL), Component.CENTER_ALIGNMENT);
	    
	    
	    this.add(controlBar);
	    //--------------------------
	    
	    //listeners
	    MyKeyListener keyListener = new MyKeyListener();
	    this.addKeyListener(keyListener);
	    MyMouseListener mouseListener = new MyMouseListener();
	    this.addMouseListener(mouseListener);

	    this.requestFocusInWindow(); 	    
	    this.setVisible(true);
	  
	    //Start the loop
	    Thread t = new Thread(new Runnable() { public void run() { run(); }}); 
	    t.start();
	}
	//loop
	  public void run() { 	    
	    while(true){
	      //update
	    	
	    	
	      try{ Thread.sleep(500);} catch (Exception exc){}  
	      this.repaint();
	    }    
	  }
	  //generate planets
	  private void generateSpaceObjects() {
		  //add converted data
		 spaceObjects.add(new Planet("Jupiter", 47856770.73097, 3.13,"CW",264233880111.9, 2.942399274322*(Math.pow(10,  15)),49399047.54706));
		 spaceObjects.add(new Planet("Saturn", 38677651.37199, 26.73,"CW",220092221634.3, 5.419910802026*(Math.pow(10,  15)),36624083.45302));
		 spaceObjects.add(new Planet("Uranus", 15531954.19155, 97.77, "C", 95857585607.38, 1.085116032958*(Math.pow(10,  16)),25738906.94686));		
		 spaceObjects.add(new Planet("Mars", 909199.4859778, 25.19, "CW",12812759845.79, 8.613651825535*(Math.pow(10,  14)),91000831.50654));
		 spaceObjects.add(new Planet("Mercury", 11370.22186106, 0, "CW",9222163428.831, 2.188751984277*(Math.pow(10,  14)),180928263.6632));
		 spaceObjects.add(new Planet("Venus", 6845.230176128, 177.36, "C",22873988963.64, 4.089500340162*(Math.pow(10,  14)),132360722.6548));
		 spaceObjects.add(new Planet("Neptune", 10203803.00854, 28.32, "CW",244243707007.3, 1.6989190415*(Math.pow(10,  16)),20523093.20432));
		 spaceObjects.add(new Planet("Earth", 1757502.456724, 23.45, "CW",24079673444.7, 5.654244462922 *(Math.pow(10,  14)),112555748.7338));
		 spaceObjects.add(new Sun("Sun", 112337373.1953, 7.25, "CW", 2.628724771336*(Math.pow(10, 12))));
		  
	  }
	 /** --------- INNER CLASSES ------------- **/
	  //solar system panel
	  private class SolarSystem extends JPanel {
		SolarSystem(Dimension screenSize) {
		      this.setBackground(Color.black);	     
		      this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
		      this.setMinimumSize(this.getPreferredSize());
		      this.setMaximumSize(this.getPreferredSize());
		}
	    public void paintComponent(Graphics g) {   
	       super.paintComponent(g); 
	       setDoubleBuffered(true);  
	       //g.drawOval(500, 300, 0.086678804, 0.086678804);
	      
	    }	    
	    
	  }
	  
	  //key listener
	   private class MyKeyListener implements KeyListener {
	  
	      public void keyTyped(KeyEvent e) {  
	      }

	      public void keyPressed(KeyEvent e) {
	      }   
	      
	      public void keyReleased(KeyEvent e) {
	      }
	    } 
	  
	   //mouse listener
	    private class MyMouseListener implements MouseListener {
	   
	      public void mouseClicked(MouseEvent e) {
	      }

	      public void mousePressed(MouseEvent e) {
	      }

	      public void mouseReleased(MouseEvent e) {
	      }

	      public void mouseEntered(MouseEvent e) {
	      }

	      public void mouseExited(MouseEvent e) {
	      }
	    } //end of mouselistener
}