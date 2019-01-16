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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

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
    private static PlanetDescription planetDesc;
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<SpaceObject> spaceObjects= new ArrayList<SpaceObject>();
    private static final double ratioD=Math.pow(10,12)*2; 
    private static final double  ratioP=Math.pow(10, 8)*7;     
    private static final double ratioS=Math.pow(10, 9)*2;
    private int backgroundX=0;
    private int backgroundY=0;
    private boolean orbits;
    private boolean on=true;
    private Clip music;
    private boolean dimensional=false;
    private boolean label;
    private boolean  texture;
    private boolean off=true;
    private String chosen;
    
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
	   
	    //------solar system panel-------
	    try {readPlanetStats();} catch (FileNotFoundException e) {
			  System.out.print("hi");
	    }
	    //generateSpaceObjects();
	    solarSystem = new SolarSystem();
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
	    
	    //---label checkbox----
	    JCheckBox labelCheck=new JCheckBox("Show planet labels: ", false);
	    labelCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelCheck.setHorizontalTextPosition(SwingConstants.LEFT);
	    labelCheck.setFocusable(false);
	    labelCheck.addActionListener(new LabelCheckListener());
	    controlBar.add(labelCheck);
	    
	    //---texture checkbox----
	    JCheckBox textureCheck=new JCheckBox("Show planet textures: ", false);
	    textureCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
	    textureCheck.setHorizontalTextPosition(SwingConstants.LEFT);
	    textureCheck.setFocusable(false);
	    textureCheck.addActionListener(new TextureCheckListener());
	    controlBar.add(textureCheck);
	    controlBar.add(Box.createRigidArea(new Dimension(0,95)));
	    
	    //---graphics panel-----
	    planetDesc = new PlanetDescription();
	    controlBar.add(planetDesc);
	    
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
     /*
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
	  */
	  private void readPlanetStats() throws FileNotFoundException {
		  File fileIn = new File("planetStats.txt");
		  Scanner textIn = new Scanner(fileIn); 
		  
		  while (textIn.hasNext()) {
			  String line= textIn.nextLine();
			  String [] data= line.split(" ");
			  //planets
			  if (!(data[0].equals("Sun"))){
				  double speed=convertPixels(Double.parseDouble(data[4])*0.000277778)/ratioS;
				  double angle= Double.parseDouble(data[6]);
				  double radius=convertPixels(Integer.parseInt(data[1]))/ratioP;
				  double speedAroundSun=convertPixels(Double.parseDouble(data[5]))/ratioS;
				  //color
				  String[] rgbValues= data[8].split(",");
				  Color color= new Color(Integer.parseInt(rgbValues[0]),Integer.parseInt(rgbValues[1]), Integer.parseInt(rgbValues[2]));
				  
				  //distance from sun
				  double multiplier;
				  if (data[3].equals("M")) { //millions 
					 multiplier=Math.pow(10, 6);
				  }
				  else { //billions
					  multiplier=Math.pow(10, 9);
				  }
				  double distance=convertPixels(Double.parseDouble(data[2])*multiplier)/ratioD;
				  
				  this.spaceObjects.add(new Planet(data[0],speed,angle,data[7],radius,color,distance, speedAroundSun, new OrbitalMove(0,0), data));
			  }
			  //sun
			  else {
				  double speed =convertPixels(Integer.parseInt(data[2]))/ratioS;
				  double angle= Double.parseDouble(data[3]);
				  double radius=convertPixels(Integer.parseInt(data[1]))/ratioP;
				  this.spaceObjects.add((new Sun(data[0],speed,angle, data[4],radius, Color.yellow, data)));
			  }
		  }		  
		  textIn.close();
		     
	  }
	  private double convertPixels (double value) {
		  double convertRatio= 3779575.17575025;
		  return value*convertRatio;
	  }
	  
	 /** ----------------------------- INNER CLASSES ------------------------------ **/
	  /** 
	   * [SolarSystem.java]
	   * @author Nicole Streltsov
	   * A class (JPanel) that contains the solar system
	   * January 2019
	   */
	  private class SolarSystem extends JPanel {
			
		private Clock clock;
		private FrameRate frameRate;
		private Star [] stars;
		private int count=600;  
		
		SolarSystem() {
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
			
			 //--------stars twinkle---------------
             //class for stars and draw them based on x and y, update evrey 10 count
			 if (count==600) {
				 stars=new Star [7*sizeAdjust.getPrevious()/3];
				 for (int i=0; i<stars.length; i++) {
					 int y=(int)((screenSize.getHeight()-1)*Math.random()+1);
					 int x=(int)((screenSize.getWidth()-300-1)*Math.random()+1);
					 stars[i]=new Star (x,y);
					 count=0;
				 }
			 }
			 //draw stars
			 for (Star each: stars) {
				 each.draw(g);
			 }
		     //-------draw solar system-------------
		   	 Iterator<SpaceObject> itr=spaceObjects.iterator();
			 while (itr.hasNext()) {
			   SpaceObject object = (SpaceObject)itr.next();   
			   if (sizeAdjust!=null) {
		    	   if (object instanceof Sun) {
		  			 if (dimensional) {
		  				 ((Sun)object).getSphere().createIcosahedron();
		  				 ((Sun)object).getSphere().drawSphere(g, ((Sun)object).getColor());
					 }
		  			 else {
		    		   ((Sun)object). drawSun(g,backgroundX, backgroundY,sizeAdjust.getPrevious());	
		  			 }
		    	   }		    	   
		    	   else  {		    		   
		    		   if (orbits) {
		    			   ((Planet)object).drawOrbit(g,backgroundX, backgroundY, sizeAdjust.getPrevious(), (int)((spaceObjects.get(8).getRadius())));
		    		   }
			    	   if (label) {
			    			  TextLabel label= new TextLabel(object.getName(),((Planet)object).getOrbitalMovement(), object.getRadius(), ((Planet)object).getDistanceFromSun());
			    			  label.draw(g, backgroundX, backgroundY,sizeAdjust.getPrevious());
			    	   }
		    		   if (dimensional) {
		    			   ((Planet)object).getSphere().createIcosahedron();
			  			   ((Planet)object).getSphere().drawSphere(g, ((Planet)object).getColor());
						}
		    		   else {
		    		   ((Planet)object).drawPlanet(g, backgroundX, backgroundY,sizeAdjust.getPrevious());
		    		   }
		    		   ((Planet)object).moveOrbital(sizeAdjust.getPrevious(),(int)(spaceObjects.get(8).getRadius()));
		    		   (((Planet)object).getOrbitalMovement()).updateRadialMovement((Planet)object, speedAdjust.getPrevious(), clock.getElapsedTime());
		    	   }
			   }
			 }
			 
	       	 //--------------extra stuff------------
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
	   * [PlanetDescription.java]
	   * @author Nicole Streltsov
	   * A class (JPanel) that contains the planet description
	   * January 2019
	   */
	  class PlanetDescription  extends JPanel {
		  private int startingX=40;
		  private int startingY=20;
		  
		  PlanetDescription(){
			  this.setBackground(Color.white);	 
		      this.setPreferredSize(new Dimension(300,(int)screenSize.getHeight()-400));
		      this.setMinimumSize(this.getPreferredSize());
		      this.setMaximumSize(this.getPreferredSize());
		  }
		  /** paintComponent *******************************************
		     * draws all the nessasary componets on the graphics panel(Solar System)
		     */
		    public void paintComponent(Graphics g) {   
		    	if (off) {
		    		g.setColor(Color.black);
		    		int size= 12;
	                Font font= new Font("Gugi", Font.PLAIN, size);
	                g.setFont(font); 
		    		g.drawString("Click a planet to view it's information.", startingX, startingY);
		    	}
		    	else {
		    		//find chosen planet
		    		
		    		
		    		
		    	}
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
				orbits=true;
			}
			else {
				orbits=false;
			}			
		}
	  }
	  /** 
	   * [LabelCheckListener.java]
	   * @author Nicole Streltsov
	   * The action listener for the label check box
	   * January 2019
	   */
	  class LabelCheckListener implements ActionListener{
		  public void actionPerformed(ActionEvent e) {
			  JCheckBox checkBox=(JCheckBox) e.getSource();
			if (checkBox.isSelected()) {
				label=true;
			}
			else {
				label=false;
			}			
		}
	  }
	  /** 
	   * [TextureCheckListener.java]
	   * @author Nicole Streltsov
	   * The action listener for the texture check box
	   * January 2019
	   */
	  class TextureCheckListener implements ActionListener{
		  public void actionPerformed(ActionEvent e) {
			  JCheckBox checkBox=(JCheckBox) e.getSource();
			if (checkBox.isSelected()) {
				texture=true;
			}
			else {
				texture=false;
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
	          //sound
	    	  if (sound.contains(e.getX(),e.getY()) && on==true){
	    		  on=false;
	    		  music.stop();
	    	  }
	    	  else if (sound.contains(e.getX(),e.getY()) && on==false){
	    		  on=true;
	    		  music.start();
	    	  }
	    	  //planets
	    	  Iterator<SpaceObject> itr=spaceObjects.iterator();
			  while (itr.hasNext()) {
				   SpaceObject object = (SpaceObject)itr.next(); 
				   ClickArea area;
				   if (object instanceof Planet) {
					   area= new ClickArea(((Planet)object).getOrbitalMovement(), object.getRadius(), "p");
				   }
				   else {
					  area=new ClickArea(object.getCenterX(),object.getCenterY(), object.getRadius(), "s" );
				   }
				   if (area.contains(e.getX(),e.getY())) {
					   off=false;
					   chosen=object.getName();
				   }
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