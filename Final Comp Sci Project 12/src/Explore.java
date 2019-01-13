//imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
	private SpeedAdjustmentListener speedAdjust;	
	private SizeAdjustmentListener sizeAdjust;
    private static SolarSystem solarSystem;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<SpaceObject> spaceObjects= new ArrayList<SpaceObject>();
    private static final double ratioD=Math.pow(10,12)*2; 
    private static final double  ratioP=Math.pow(10, 8)*7;     
    private static final double ratioS=Math.pow(10, 9)*2;
    private int backgroundX=0;
    private int backgroundY=0;
    private boolean selected;
    private boolean on=true;
    private Clip music;
    
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
	   
	    //------solor system panel-------
	    generateSpaceObjects();
	    //updateRadialMovement(1);
	    solarSystem = new SolarSystem(screenSize);
	    this.add(solarSystem);
	    
	    //-------control bar panel------
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
	    JScrollBar sizeControl=new JScrollBar(JScrollBar.HORIZONTAL,1, 10, 1, 100);
	    sizeAdjust =new SizeAdjustmentListener(1,sizeControl);
	    sizeControl.addAdjustmentListener(sizeAdjust);
	    sizeControl.setValue(1);   
	    controlBar.add(sizeControl);
	    
	    //----speed label-----
	    JLabel speedLabel = new JLabel("speed:");
	    sizeLabel.setFont(new Font("Gugi",Font.PLAIN, 15));
	    speedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    controlBar.add(speedLabel);
	    
	    //----scrollbar speed----
	    JScrollBar speedControl=new JScrollBar(JScrollBar.HORIZONTAL, 1, 10, 1, 100);
	    speedAdjust=new SpeedAdjustmentListener(1,speedControl);
	    speedControl.addAdjustmentListener(speedAdjust);    
	    speedControl.setValue(1);                                                           
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
	}
	 /** generateSpaceObjects *******************************************
	     * adds all of the space objects' data and their converted values in pixels
	     */
	  private void generateSpaceObjects() {     //implement read from file
		  //add converted data
		 this.spaceObjects.add(new Planet("Jupiter", 47856770.73097/ratioS, 3.13,"CW",264233880111.9/ratioP, new Color(234,192,134),2.942399274322*(Math.pow(10,15))/ratioD,49399047.54706/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Saturn", 38677651.37199/ratioS, 26.73,"CW",220092221634.3/ratioP,Color.orange, 5.419910802026*(Math.pow(10, 15))/ratioD,36624083.45302/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Uranus", 15531954.19155/ratioS, 97.77, "C", 95857585607.38/ratioP, new Color(0,255,255), 1.085116032958*(Math.pow(10,  16))/ratioD,25738906.94686/ratioS, new OrbitalMove(0,0)));		
		 this.spaceObjects.add(new Planet("Mars", 909199.4859778/ratioS, 25.19, "CW",12812759845.79/ratioP, Color.red, 8.613651825535*(Math.pow(10,  14))/ratioD,91000831.50654/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Mercury", 11370.22186106/ratioS, 0, "CW",9222163428.831/ratioP,Color.white, 2.188751984277*(Math.pow(10,  14))/ratioD,180928263.6632/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Venus", 6845.230176128/ratioS, 177.36, "C",22873988963.64/ratioP,Color.gray, 4.089500340162*(Math.pow(10,  14))/ratioD,132360722.6548/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Neptune", 10203803.00854/ratioS, 28.32, "CW",244243707007.3/ratioP, new Color(128,0,128), 1.6989190415*(Math.pow(10,  16))/ratioD,20523093.20432/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Planet("Earth", 1757502.456724/ratioS, 23.45, "CW",24079673444.7/ratioP,Color.blue, 5.654244462922 *(Math.pow(10,  14))/ratioD,112555748.7338/ratioS, new OrbitalMove(0,0)));
		 this.spaceObjects.add(new Sun("Sun", 112337373.1953/ratioS, 7.25, "CW", 2.628724771336*(Math.pow(10, 12))/ratioP, Color.yellow));
		  
	  }
	 /** ----------------------------- INNER CLASSES ------------------------------ **/
	  /** 
	   * [SolarSystem.java]
	   * @author Nicole Streltsov
	   * A class (JPanel) that contains the solar system
	   * January 2019
	   */
	  private class SolarSystem extends JPanel {
		private int centerX=(int)(screenSize.getWidth()/2);	     
		private int centerY=(int)(screenSize.getHeight()/2)-100;	
		private Clock clock;
		FrameRate frameRate;
		
		SolarSystem(Dimension screenSize) {
		      this.setBackground(Color.black);	 
		      this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
		      this.setMinimumSize(this.getPreferredSize());
		      this.setMaximumSize(this.getPreferredSize());
		      clock=new Clock();
		      frameRate=new FrameRate();
		}
		/** paintComponent *******************************************
	     * draws all the nessasary componets on the graphics panel(Solar System)
	     */
	    public void paintComponent(Graphics g) {   
	    	 super.paintComponent(g); 
	       	 setDoubleBuffered(true); 
	       	 
	       	 
	       	 //update
	 		 clock.update();
			 frameRate.update();
			
			 //stars twinkle
			 int count=10;        //class for stars and draw them based on x and y, update evrey 10 count
			 int x=0, y=0;
			 if (count==10) {
				 final int NUM_STARS=1000;
				 for (int i=0; i<NUM_STARS; i++) {
					 x=(int)((screenSize.getHeight()-300-1)*Math.random()+1);
					 y=(int)((screenSize.getWidth()-300-1)*Math.random()+1);
					 count=0;
				 }
			 }
			 g.drawOval(x, y, 5,5);
			 
		     //draw solar system
		   	 Iterator<SpaceObject> itr=spaceObjects.iterator();
			 while (itr.hasNext()) {
			   SpaceObject object = (SpaceObject)itr.next();   
			   
			   if (sizeAdjust!=null) {
		    	   if (object instanceof Sun) {
		    		   ((Sun)object). drawSun(g,centerX, centerY, backgroundX, backgroundY,sizeAdjust.getPrevious());	   
		    	   }		    	   
		    	   else  {		    		   
		    		   if (selected) {
		    			   ((Planet)object).drawOrbit(g, centerX,centerY, backgroundX, backgroundY, sizeAdjust.getPrevious(), (int)((spaceObjects.get(8).getRadius())));
		    		   }
		    		   ((Planet)object).drawPlanet(g, centerX,centerY, backgroundX, backgroundY,sizeAdjust.getPrevious());
		    		   ((Planet)object).moveOrbital(sizeAdjust.getPrevious(),(int)(spaceObjects.get(8).getRadius()));
		    		   (((Planet)object).getOrbitalMovement()).updateRadialMovement((Planet)object, speedAdjust.getPrevious(), clock.getElapsedTime());
		    	   }
			   }
			 }
			 
	       	 //extra stuff
	       	 g.setColor(Color.white);
		   	 g.drawString("Note: Planet sizes are inflated (not exactly to scale)", 20, 30);  
		   	 frameRate.draw(g,20,45);
		   	 BufferedImage arrowKeys = null;
		     try {arrowKeys = ImageIO.read(new File("images/arrowKeys.png"));} catch (IOException e) {}
		     g.drawImage(arrowKeys,15, (int)screenSize.getHeight()-100, null);
		     g.drawString("View Controls: ", 20, (int)screenSize.getHeight()-90);
		     BufferedImage soundOn = null;
		     try {soundOn = ImageIO.read(new File("images/volumeOn.png"));} catch (IOException e) {}
		     BufferedImage soundOff = null;
		     try {soundOff = ImageIO.read(new File("images/volumeOff.png"));} catch (IOException e) {}
		     if (on) {
		    	g.drawImage(soundOn, (int)screenSize.getWidth()-330, 5, null);
		     }
		     else {
		    	 g.drawImage(soundOff, (int)screenSize.getWidth()-330, 5, null);
		     }
		     count++;
		     repaint();
	    }
	  }
	  /** 
	   * [SizeAdjustmentListener.java]
	   * @author Nicole Streltsov
	   * The adjustment listener that regulates the size of the solar system
	   * January 2019
	   */
	  class SizeAdjustmentListener implements AdjustmentListener {     
		 private int previous;
		 private JScrollBar sizeControl;
		 
		 /** SizeAdjustmentListener *******************************************
		     * constructor for adjustment listener
		     * @param the size scroll bar
		     */
		 SizeAdjustmentListener (int previous, JScrollBar sizeControl){
			 this.sizeControl=sizeControl;
			 this.previous=previous;
		 }
		 /** getPrevious *******************************************
		     * returns the previous value of the scroll bar
		     * @return previous integer value
		     */
		 public int getPrevious() {
			 return this.previous;
		 }
		 /** adjustmentValueChanged *******************************************
		     * checks if the scroll bars size adjustement is changed
		     */
		 public void adjustmentValueChanged(AdjustmentEvent e) {		
			 int type = e.getAdjustmentType();			 
			 if (type==AdjustmentEvent.TRACK) {
				 int currentValue = sizeControl.getValue();
				 previous=currentValue;
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
		  private JScrollBar speedControl;
		  
		  /** SpeedAdjustmentListener *******************************************
		     * constructor to initilize values
		     * @param starting value integer and the scroll bar variable
		     */	
		  SpeedAdjustmentListener(int startingValue, JScrollBar speedControl){
			  this.previous=startingValue;
			  this.speedControl=speedControl;
		  }
		  /** getPrevious *******************************************
		     * gets the previous value from the listener
		     * @return previous integer value
		     */		 
		  private int getPrevious() {
			  return this.previous;
		  }
		  /** adjustmentValueChanged *******************************************
		     * checks if the scroll bar speed adjustement is changed
		     * @param adjustment event 
		     */		  
		  public void adjustmentValueChanged(AdjustmentEvent e) {
			 int type = e.getAdjustmentType();		
			 if (type==AdjustmentEvent.TRACK) {
				 int currentValue = speedControl.getValue();
				 previous=currentValue;
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
	     Rectangle sound=new Rectangle((int)screenSize.getWidth()-340,5, 30, 30);
	      public void mouseClicked(MouseEvent e) {
	    	  if (sound.contains(e.getX(),e.getY()) && on==true){
	    		  on=false;
	    		  music.stop();
	    	  }
	    	  else if (sound.contains(e.getX(),e.getY()) && on==false){
	    		  on=true;
	    		  music.start();
	    	  }
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