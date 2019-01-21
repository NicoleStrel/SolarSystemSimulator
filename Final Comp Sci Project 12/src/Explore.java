//imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;


/** 
 * [Explore.java]
 * @author Nicole Streltsov
 * A class (Jframe) that has the main solar system movement and controls
 * January 2019
 */
class Explore extends JFrame{
	//declare variables
	private JFrame explore;
	private ThreeDPanel tdpanel;
	private SpeedAdjustmentListener speedAdjust;	
	private SizeAdjustmentListener sizeAdjust;
    private SolarSystem solarSystem;
    private static PlanetDescription planetDesc;
    private JPanel controlBar;
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ArrayList<SpaceObject> spaceObjects= new ArrayList<SpaceObject>();
    private static final double ratioD=Math.pow(10,12); 
    private static final double  ratioP=Math.pow(10, 8)*7;     
    private static final double ratioS=Math.pow(10, 9)*2;
    private int backgroundX=0;
    private int backgroundY=0;
    private boolean orbits;
    private boolean on=true;
    private Clip music;
    private boolean threeD=false;
    private boolean label;
    private boolean  texture;
    private boolean axialTilt;
    private boolean play=true;
    
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
	    this.getContentPane().add(solarSystem);
	    
	    //-------control bar panel------
	    controlBar = new JPanel();
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
	        
	    //---axial tilt checkbox----
	    JCheckBox axialCheck=new JCheckBox("Show axial tilts: ", false);
	    axialCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
	    axialCheck.setHorizontalTextPosition(SwingConstants.LEFT);
	    axialCheck.setFocusable(false);
	    axialCheck.addActionListener(new AxialCheckListener());
	    controlBar.add(axialCheck);
	    controlBar.add(Box.createRigidArea(new Dimension(0,20)));
	   
	    //------option drop down menu------
	    String[] options = { "Two-Dimensional", "Three-Dimensional" };
		JComboBox <String>optionBox = new JComboBox<String>(options);
		optionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionBox.setSelectedIndex(0);
		optionBox.addActionListener(new OptionBoxListener());
		optionBox.setFocusable(false);
		controlBar.add(optionBox);
		controlBar.add(Box.createRigidArea(new Dimension(0,40)));
		
	    //---graphics panel-----
	    planetDesc = new PlanetDescription(true, null, spaceObjects);
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
	    
	    
	    this.getContentPane().add(controlBar);
	    
	    //-------------------------------------------------		    
	    //listeners
	    MyKeyListener keyListener = new MyKeyListener();
	    this.addKeyListener(keyListener);
	    MyMouseListener mouseListener = new MyMouseListener();
	    
	    //set up 3d panel beforehand- takes a while to render
	    tdpanel = new ThreeDPanel();
	    
	    this.addMouseListener(mouseListener);
	    this.requestFocusInWindow(); 
	    this.pack();
	    this.setVisible(true);
	}
	/** readPlanetStats *******************************************
     * reads all the planet information from the text file and stores it into variables
     */
	  private void readPlanetStats() throws FileNotFoundException {
		  File fileIn = new File("planetStats.txt");
		  Scanner textIn = new Scanner(fileIn); 
		  
		  while (textIn.hasNext()) {
			  String line= textIn.nextLine();
			  String [] data= line.split("[|]");
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
		    		   ((Sun)object). drawSun(g,backgroundX, backgroundY,sizeAdjust.getPrevious());	
		    	   }		    	   
		    	   else  {		    		   
		    		   if (orbits) {
		    			   ((Planet)object).drawOrbit(g,backgroundX, backgroundY, sizeAdjust.getPrevious(), (int)((spaceObjects.get(8).getRadius())));
		    		   }
			    	   if (label) {
			    			  TextLabel label= new TextLabel(object.getName(),((Planet)object).getOrbitalMovement(), object.getRadius(), ((Planet)object).getDistanceFromSun());
			    			  label.draw(g, backgroundX, backgroundY,sizeAdjust.getPrevious());
			    	   }
		    		   ((Planet)object).drawPlanet(g, backgroundX, backgroundY,sizeAdjust.getPrevious());	
		    		   ((Planet)object).moveOrbital(sizeAdjust.getPrevious(),(int)(spaceObjects.get(8).getRadius()));
		    		   if (axialTilt) {
		    			   ((Planet)object).drawAxis(g, sizeAdjust.getPrevious(), backgroundX, backgroundY);
		    		   }
		    		   if (play) {
			    		   (((Planet)object).getOrbitalMovement()).updateRadialMovement((Planet)object, speedAdjust.getPrevious(), clock.getElapsedTime());
		    		   }
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
		     if (play) {
		    	 g.fillRect((int)screenSize.getWidth()-365, 5, 5, 20);
		    	 g.fillRect((int)screenSize.getWidth()-355,5, 5, 20);
		     }
		     else {
		    	 int [] xPoints= {(int)screenSize.getWidth()-365, (int)screenSize.getWidth()-365, (int)screenSize.getWidth()-350};
		    	 int [] yPoints= {5,25,15};
		    	 g.fillPolygon(xPoints, yPoints,3);
		     }
		     count++;
		     repaint();
	    }
	  }
	  /** 
	   * [ThreeDPanel.java]
	   * @author Nicole Streltsov
	   * A class (JPanel) that contains the 3d rendering
	   * January 2019
	   */
	  class ThreeDPanel extends JPanel{
			private JPanel threeDimension;
			private Clock clock;
			private FrameRate frameRate;
			private SimpleUniverse universe;
			private Canvas3D canvas;
			
			public ThreeDPanel() { 
			    this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
			    this.setMinimumSize(this.getPreferredSize());
			    this.setMaximumSize(this.getPreferredSize());
			    this.setLayout(new BorderLayout());
			    clock=new Clock();
			    frameRate=new FrameRate();
			    
			   //set up graphics
			    GraphicsConfiguration config= SimpleUniverse.getPreferredConfiguration();
		        canvas= new Canvas3D(config);
		        //canvas.setFocusable(true);
				//canvas.getView().repaint();
		        universe = new SimpleUniverse(canvas);
				this.add ("Center", canvas);
				BranchGroup scene = createSceneGraph();
				universe.getViewingPlatform().setNominalViewingTransform();
				//universe.getViewer().getView().setBackClipDistance(100.0);							
				universe.addBranchGraph(scene);
				
			}
			private void convert(int number) {
				//maybe
			}
			
			private BranchGroup createSceneGraph(){	
				BranchGroup root = new BranchGroup();
				
				//size scaling
			    TransformGroup sceneGraph = new TransformGroup();
			    sceneGraph.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			    //Transform3D tsize = new Transform3D();
			    //tsize.setScale(sizeAdjust.getPrevious()/100);
			    //sceneGraph.setTransform(tsize);
			    root.addChild(sceneGraph);
			    
			    //bounds for lighting
			    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
			    Background bg = new Background();
			    bg.setApplicationBounds(bounds);
			    sceneGraph.addChild(bg);
			    
			    //AmbientLight aLgt = new AmbientLight(new Color3f(0.0f, 0.0f, 0.0f));
			    //aLgt.setInfluencingBounds(bounds);
			    //sceneGraph.addChild(aLgt);
			    
				//add planets and stuff here
				Iterator<SpaceObject> itr=spaceObjects.iterator();
			    while (itr.hasNext()) {
			       SpaceObject object = (SpaceObject)itr.next(); 
			       sceneGraph.addChild(object.getTransformGroup());
				   if (object instanceof Sun) {
					   ((Sun)object).renderSphere(backgroundX,backgroundY,sizeAdjust.getPrevious());//backgroundY is not used  for now
					   ((Sun)object).startingPos(backgroundX,backgroundY,sizeAdjust.getPrevious());
					   SpotLight light =((Sun)object).addSpotLight(backgroundX,backgroundY,sizeAdjust.getPrevious());
					   light.setInfluencingBounds(bounds);
					   
				   }
				   else {
					   ((Planet)object).renderSphere(backgroundX,backgroundY,sizeAdjust.getPrevious());
					   ((Planet)object).startingPos(backgroundX,backgroundY,sizeAdjust.getPrevious());
					   //rotation
					   TransformGroup target=spaceObjects.get(8).getTransformGroup();
					   RotationInterpolator rotator= ((Planet)object).addOrbital(target);
				       rotator.setSchedulingBounds(bounds);
					  
				   }
				 }	

				 root.compile();
				 return root;
				
			}
			 private DirectionalLight createSphereLight() {
				 //ambient light >light present in room
				 
				 
				 
				 Color3f sphereLightColor =new Color3f(1.0f, 1.0f, 1.0f);
				 Vector3f sphereLightDirection= new Vector3f(-1.0f, -0.5f, -0.5f);
				 BoundingSphere bounds= new BoundingSphere(new Point3d(0,0,0), 100);
				 DirectionalLight sphereLight= new DirectionalLight(sphereLightColor,sphereLightDirection);
				 sphereLight.setInfluencingBounds(bounds);
				 return sphereLight;
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
	   * [AxialCheckListener.java]
	   * @author Nicole Streltsov
	   * The action listener for the axial tilt check box
	   * January 2019
	   */
	  class AxialCheckListener implements ActionListener{
		  public void actionPerformed(ActionEvent e) {
			  JCheckBox checkBox=(JCheckBox) e.getSource();
			if (checkBox.isSelected()) {
				axialTilt=true;
			}
			else {
				axialTilt=false;
			}			
		}
	  }
	  /** 
	   * [OptionBoxListener.java]
	   * @author Nicole Streltsov
	   * The action listener for the option drop down menu
	   * January 2019
	   */
	  class OptionBoxListener implements ActionListener{
		  public void actionPerformed(ActionEvent e) {
		      JComboBox <String> cb = (JComboBox<String>)e.getSource();
		       String dimension = (String)cb.getSelectedItem();
		       if (dimension.equals("Two-Dimensional")) {
		    	   explore.getContentPane().removeAll();
				   explore.getContentPane().add(solarSystem);
				   explore.getContentPane().add(controlBar);	
				   explore.revalidate();
				   explore.repaint();
		       }
		       else if(dimension.equals("Three-Dimensional")) {
		    	   explore.getContentPane().removeAll();
				   explore.getContentPane().add(tdpanel);
				   explore.getContentPane().add(controlBar);
				   explore.revalidate();
				   explore.repaint();
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
	    	  Rectangle sound=new Rectangle((int)screenSize.getWidth()-340,5, 30, 30);
		      Rectangle stop=new Rectangle((int)screenSize.getWidth()-365, 5, 15, 15);
	    	  //sound
	    	  if (sound.contains(e.getX(),e.getY()) && (on==true)){
	    		  on=false;
	    		  music.stop();
	    	  }
	    	  else if (sound.contains(e.getX(),e.getY()) && (on==false)){
	    		  on=true;
	    		  music.start();
	    	  }
	    	  //stop
	    	  if (stop.contains(e.getX(),e.getY()) && (play==true)){
	    		  play=false;
	    		 
	    	  }
	    	  else if (stop.contains(e.getX(),e.getY()) && (play==false)){
	    		  play=true;
	    	  }
	    	  //planets selection
	    	  Iterator<SpaceObject> itr=spaceObjects.iterator();
			  while (itr.hasNext()) {
				   SpaceObject object = (SpaceObject)itr.next(); 
				   ClickArea area= new ClickArea(object, sizeAdjust.getPrevious(),  backgroundX, backgroundY); 
				   if (area.contains(e.getX(),e.getY())) {
					   planetDesc.setValues(false, object.getName());
				   }
			  }
	      }

	      public void mouseReleased(MouseEvent e) {
	      }

	      public void mouseEntered(MouseEvent e) {
	      }

	      public void mouseExited(MouseEvent e) {
	      }
	    } 
} //end of Explore class