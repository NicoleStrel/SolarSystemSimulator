//imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/** 
 * [Explore.java]
 * @author Nicole Streltsov
 * A class (Jframe) that has the main solar system movement and controls
 * January 2019
 */
class Explore extends JFrame{
	//declare variables
	private JFrame explore;
	private JScrollBar sizeControl, speedControl;	
    private static SolarSystem solarSystem;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<SpaceObject> spaceObjects= new ArrayList<SpaceObject>();
    private double ratioD=Math.pow(10,12)*2; 
    private double ratioP=Math.pow(10, 8)*7;     
    private final double DP_RATIO=ratioD/ratioP;
    private double ratioS=Math.pow(10, 8);
    private static final double TIME=0.2; //in seconds 
    private int backgroundX=0;
    private int backgroundY=0;
    private int centerX=(int)(screenSize.getWidth()/2);	     
    private int centerY=(int)(screenSize.getHeight()/2)-100;	
    private boolean selected;
    
    /** getRatioD*******************************************
     * returns the current distance ratio
     */
	public double getRatioD() {
		return this.ratioD;
	}
	 /** getRatioP*******************************************
     * returns the current planet size ratio
     */
	public double getRatioP() {
		return this.ratioP;
	}
    
    /** Explore constructor*******************************************
     * contstructor to set up the properties of the Jframe and its components
     */
	Explore(){
		super("Explore"); 
		this.explore = this; 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
	    this.setUndecorated(true);  
	    this.setResizable(false);
		    
	    //Set up frame 
	    generateSpaceObjects();
	    updateRadialMovement();
	    //fillDefaultCoordinates();
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
	    controlBar.add(Box.createRigidArea(new Dimension(0,20)));

	    //-----control label----
	    BufferedImage controls = null;
		try {controls = ImageIO.read(new File("images/controls.png"));} catch (IOException e) {}
	    JLabel controlLabel = new JLabel(new ImageIcon(controls));
	    controlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    controlBar.add(controlLabel);
	    controlBar.add(Box.createRigidArea(new Dimension(0,30)));
	    
	    //----size label-----
	    JLabel sizeLabel = new JLabel("size:");
	    sizeLabel.setFont(new Font("Gugi",Font.PLAIN, 15));
	    sizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    controlBar.add(sizeLabel);
	    
	    //---scroll bar size----
	    sizeControl=new JScrollBar(JScrollBar.HORIZONTAL,0, 300, 0, 1000000);
	    sizeControl.addAdjustmentListener(new SizeAdjustmentListener( ));
	    controlBar.add(sizeControl);
	    
	    //----speed label-----
	    JLabel speedLabel = new JLabel("speed:");
	    sizeLabel.setFont(new Font("Gugi",Font.PLAIN, 15));
	    speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    controlBar.add(speedLabel);
	    
	    //----scrollbar speed----
	    speedControl=new JScrollBar(JScrollBar.HORIZONTAL, 0, 300, 0, 1000000);
	    speedControl.addAdjustmentListener(new SpeedAdjustmentListener(300000));    
	    speedControl.setValue(300000);
	    controlBar.add(speedControl);
	    
	    //---orbits checkbox----
	    controlBar.add(Box.createRigidArea(new Dimension(0,30)));
	    JCheckBox orbitCheck=new JCheckBox("Show planet orbits: ", false);
	    orbitCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
	    orbitCheck.setHorizontalTextPosition(SwingConstants.LEFT);
	    orbitCheck.setFocusable(false);
	    orbitCheck.addActionListener(new OrbitsCheckListener());
	    controlBar.add(orbitCheck);
	    
	    //---------audio-------------
	    File musicFile = new File("audio/explore.wav");
	    
	    try {
	    	AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
	    	Clip music = AudioSystem.getClip();
	    	music.open(audioIn);
	    	music.loop(Clip.LOOP_CONTINUOUSLY);	    	
	    } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	         e.printStackTrace();
	    }
	    
	    
	    this.add(controlBar);
	    //-------------------------------------------------
	    
	    //listeners
	    MyKeyListener keyListener = new MyKeyListener();
	    this.addKeyListener(keyListener);
	    MyMouseListener mouseListener = new MyMouseListener();
	    
	    this.addMouseListener(mouseListener);
	    this.requestFocusInWindow(); 
	    this.pack();
	    this.setVisible(true);
	  
	    //Start the loop
	    Thread t = new Thread(new Runnable() {
		    	public void run() { 
		    		while(true){
		    		      //update  
		    		      try{ Thread.sleep(200);} catch (Exception exc){}  
		    		      moveOrbital(spaceObjects.get(8).getRadius());
		    		      solarSystem.repaint();
		    		      updateRadialMovement();
		    		    }    
		    	}
	    	}); 
	    t.start();
	}
	/** updateRadialMovement *******************************************
     * updates the radian value that the planets move by
     */
	private void updateRadialMovement() {
		 Iterator<SpaceObject> itr=spaceObjects.iterator();
		 while (itr.hasNext()) {
			 SpaceObject object = (SpaceObject)itr.next();
			 if (object instanceof Planet){
				 double speed=((Planet)object).getSpeedRotateSun()/ratioS;
				 double radian=TIME*speed+((Planet) object).getOrbitalMovement().getRadian();			 
				 ((Planet) object).getOrbitalMovement().setRadian(radian);			 			 
			 }
		 }
	}
	/** moveOrbital *******************************************
     * moves all the planets counter-clockwise around the sun
     */	
	 private void moveOrbital(double sunRadius) {
		 Iterator<SpaceObject> itr=spaceObjects.iterator();
		 while (itr.hasNext()) {
			 SpaceObject object = (SpaceObject)itr.next();
			 if (object instanceof Planet){
				 double radius=(((Planet)object).getDistanceFromSun()/ratioD)+(sunRadius/ratioP)+(object.getRadius()/ratioP);		
				 double radian=((Planet)object).getOrbitalMovement().getRadian();
				 
				 int newX=(int)(radius-radius*Math.cos(radian));	
				 int newY=(int)(radius*Math.sin(radian));
				 
				 ((Planet)object).getOrbitalMovement().setOrbitalX(newX);
				 ((Planet)object).getOrbitalMovement().setOrbitalY(newY);		 
			 }
		 }
	 }
	 /** generateSpaceObjects *******************************************
	     * adds all of the space objects' data and their converted values in pixels
	     */
	  private void generateSpaceObjects() {     //implement read from file
		  //add converted data
		 this.spaceObjects.add(new Planet("Jupiter", 47856770.73097, 3.13,"CW",264233880111.9, new Color(234,192,134),2.942399274322*(Math.pow(10,15)),49399047.54706, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Saturn", 38677651.37199, 26.73,"CW",220092221634.3,Color.orange, 5.419910802026*(Math.pow(10, 15)),36624083.45302, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Uranus", 15531954.19155, 97.77, "C", 95857585607.38, new Color(0,255,255), 1.085116032958*(Math.pow(10,  16)),25738906.94686, new OrbitalMove(0,0)));		
		 this.spaceObjects.add(new Planet("Mars", 909199.4859778, 25.19, "CW",12812759845.79, Color.red, 8.613651825535*(Math.pow(10,  14)),91000831.50654, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Mercury", 11370.22186106, 0, "CW",9222163428.831,Color.white, 2.188751984277*(Math.pow(10,  14)),180928263.6632, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Venus", 6845.230176128, 177.36, "C",22873988963.64,Color.gray, 4.089500340162*(Math.pow(10,  14)),132360722.6548, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Neptune", 10203803.00854, 28.32, "CW",244243707007.3, new Color(128,0,128), 1.6989190415*(Math.pow(10,  16)),20523093.20432, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Earth", 1757502.456724, 23.45, "CW",24079673444.7,Color.blue, 5.654244462922 *(Math.pow(10,  14)),112555748.7338, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Sun("Sun", 112337373.1953, 7.25, "CW", 2.628724771336*(Math.pow(10, 12)), Color.yellow));
		  
	  }
	 /** ----------------------------- INNER CLASSES ------------------------------ **/
	  /** 
	   * [SolarSystem.java]
	   * @author Nicole Streltsov
	   * A class (JPanel) that contains the solar system
	   * January 2019
	   */
	  private class SolarSystem extends JPanel {
		SolarSystem(Dimension screenSize) {
		      this.setBackground(Color.black);	 
		      this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
		      this.setMinimumSize(this.getPreferredSize());
		      this.setMaximumSize(this.getPreferredSize());
		}
		/** paintComponent *******************************************
	     * draws all the nessasary componets on the graphics panel(Solar System)
	     */
	    public void paintComponent(Graphics g) {    //will implement drawable   //TO DO
	    	 super.paintComponent(g); 
	       	 setDoubleBuffered(true);    
	       	 g.setColor(Color.white);
		   	 g.drawString("Note: Planet sizes are inflated (not exactly to scale)", 20, 30);   
		   	 
		     //draw solar system
		   	 Iterator<SpaceObject> itr=spaceObjects.iterator();
			 while (itr.hasNext()) {
			   SpaceObject object = (SpaceObject)itr.next();     
	
		    	   if (object instanceof Sun) {
		    		   ((Sun)object). drawSun(g,centerX, centerY, backgroundX, backgroundY,ratioP);	   
		    	   }		    	   
		    	   else {		    		   
		    		   if (selected) {
		    			   ((Planet)object).drawOrbit(g, centerX,centerY, backgroundX, backgroundY, ratioD, ratioP, (int)((spaceObjects.get(8).getRadius())/ratioP));
		    		   }
		    		   ((Planet)object).drawPlanet(g, centerX,centerY, backgroundX, backgroundY, ratioD, ratioP);
		    	   }
			 } 
	    }
	  }
	  /** 
	   * [SizeAdjustmentListener.java]
	   * @author Nicole Streltsov
	   * The adjustment listener that regulates the size of the solar system
	   * January 2019
	   */
	  class SizeAdjustmentListener implements AdjustmentListener {
		 private int previous=0;
		 /** adjustmentValueChanged *******************************************
		     * checks if the scroll bars size adjustement is changed
		     */
		 public void adjustmentValueChanged(AdjustmentEvent e) {		
			 int type = e.getAdjustmentType();			 
			 if (type==AdjustmentEvent.TRACK) {
				 int currentValue = sizeControl.getValue() + sizeControl.getVisibleAmount();	
				 if (currentValue>previous) {
					 ratioD+=(currentValue-previous)*Math.pow(10,5)*DP_RATIO;
					 ratioP+=(currentValue-previous)*Math.pow(10,5);	
					 previous=currentValue;
					 
				 }
				 else if (currentValue<previous){
					 ratioD-=(previous-currentValue)*Math.pow(10,5)*DP_RATIO;
					 ratioP-=(previous-currentValue)*Math.pow(10,5);
					 previous=currentValue;
				 }
			 }		
		}
	  }
	  /** 
	   * [SpeedAdjustmentListener.java]
	   * @author Nicole Streltsov
	   * The adjustment listener that regulates the speed of the solar system
	   * January 2019
	   */
	  
	  class SpeedAdjustmentListener implements AdjustmentListener {
		  private int previous;
		  
		  SpeedAdjustmentListener(int startingValue){
			  this.previous=startingValue;
			  
			  System.out.println("1: ratioS: " + ratioS);
			  System.out.println("1: startingValue: " + startingValue);
		  }
		  /** adjustmentValueChanged *******************************************
		     * checks if the scroll bar speed adjustement is changed
		     */		  
		  public void adjustmentValueChanged(AdjustmentEvent e) {
			 int type = e.getAdjustmentType();		
			 if (type==AdjustmentEvent.TRACK) {
				 int currentValue = speedControl.getValue() + speedControl.getVisibleAmount();	
				 
				 if (currentValue>previous) {
					 System.out.println("1:  "+currentValue);
					 ratioS+=(currentValue-previous)*Math.pow(10,10);
					 previous=currentValue;
				 }
				 else if (currentValue<previous){
					 System.out.println("2:  "+currentValue);
					 ratioS-=(currentValue-previous)*Math.pow(10,10);
					 previous=currentValue;
					 
				 } else {
					 System.out.println("3:  "+currentValue);
				 }
				 System.out.println("2: ratioS: " + ratioS);
			 }
		  }			  
	  }
	  /** 
	   * [OrbitsCheckListener.java]
	   * @author Nicole Streltsov
	   * The action listener for the orbit check box
	   * January 2019
	   */
	  class OrbitsCheckListener implements ActionListener{
		  public void actionPerformed(ActionEvent e) {
			  JCheckBox checkBox=(JCheckBox) e.getSource();
			if (checkBox.isSelected()) {
				selected=true;
			}
			else {
				selected=false;
			}			
		}
	  }
	  /** 
	   * [MyKeyListener.java]
	   * @author Nicole Streltsov
	   * The key listener that regulates inputs from the keyboard
	   * January 2019
	   */
	   private class MyKeyListener implements KeyListener {
	  
	      public void keyTyped(KeyEvent e) {  
	      }

	      public void keyPressed(KeyEvent e) {
		    	  if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    		  explore.dispose();
		 		  System.exit(0);
		    	  }
		    	  else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		    		  backgroundX-=7;		    	    
		    	  }
		    	  else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		    		  backgroundX+=7;
		    	  }
		    	  else if (e.getKeyCode() == KeyEvent.VK_UP) {
		    		  backgroundY+=7;
		    	  }
		    	  else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    		  backgroundY-=7;
		    	  }
	      }   
	      
	      public void keyReleased(KeyEvent e) {
	      }
	    } 	  
	   /** 
		   * [MyMouseListener.java]
		   * @author Nicole Streltsov
		   * The key listener that regulates inputs from the mouse
		   * January 2019
		   */
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
	    } 
} //end of Explore class