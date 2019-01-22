//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

/** 
* [SpaceObject.java]
* @author Nicole Streltsov
* Defines the characteristics of a space object in the program (abstract class)
* January 2019
*/
abstract class SpaceObject{
	//declare variables
	private double axialTilt; //degreees
	private double speedRotates; //pixels/s
	private String directionRotates;
	private String [] data;
	private double radius;
	private Color color;
	private String name;
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int centerX=(int)(screenSize.getWidth()/2);	     
	public final int centerY=(int)(screenSize.getHeight()/2)-100;
	private TransformGroup transformAll;
	private TransformGroup innerTransform;
	
	/** SpaceObject *******************************************
	  * constructor for the space object class
	  * @param the name of the space object
	  * @param the speed it rotates around itself
	  * @param the angle of its tilt in degrees
	  * @param the String direction the planet rotates around itself
	  * @param the radius of the planet
	  * @param the color of the planet
	  * @param its array of data from the text file
	  */
	SpaceObject(String name, double speedRotates, double axialTilt, String directionRotates, double radius, Color color, String [] data){
		this.name=name;
		this.speedRotates=speedRotates;
		this.axialTilt=axialTilt;
		this.directionRotates= directionRotates;
		this.radius=radius;
		this.color=color;
		this.data=data;
		resetTransforms();
	}
	/** getAngle *******************************************
	  * getter for axial tilt angle
	  * @return the double value for the angle
	  */
	public double getAngle() {
		return this.axialTilt;
	}
	/** getName *******************************************
	  * getter for name of the space object
	  * @return the string name of the object
	  */
	public String getName() {
		return this.name;
	}
	/** getRadius *******************************************
	  * getter for radius of the space object
	  * @return the double radius of the object in pixels
	  */
	public double getRadius() {
		return this.radius;
	}
	/** getColor *******************************************
	  * getter for color of the space object
	  * @return the color of the object
	  */
	public Color getColor() {
		return this.color;
	}
	/** getCenterX *******************************************
	  * getter for x reference point of the solar system (same)
	  * @return the integer reference point
	  */
	public int getCenterX() {
		return this.centerX;
	}
	/** getCenterY *******************************************
	  * getter for y reference point of the solar system (same)
	  * @return the integer reference point
	  */
	public int getCenterY() {
		return this.centerY;
	}
	/** getData *******************************************
	  * getter for array of original data of the space object
	  * @return the String array of data
	  */
	public String [] getData() {
		return this.data;
	}
	/** getTransformGroup *******************************************
	  * getter for the main Transform group of the object
	  * @return the main TransformGroup object
	  */
	public TransformGroup getTransformGroup() {
		return this.transformAll;
	}
	/** getInnerT*******************************************
	  * getter for the inner Transform group of the object
	  * @return the inner TransformGroup object
	  */
	public TransformGroup getInnerT() {
		return this.innerTransform;
	}

	/** resetTransforms*******************************************
	  * regenerates new transform groups for the start or update
	  */
	public void resetTransforms() {
		transformAll = new TransformGroup();
		transformAll.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		innerTransform = new TransformGroup();
		transformAll.addChild(innerTransform);
	}
	/** convertToFloat *******************************************
	  * converts the pixel value to a millipoint floating value
	  * @param the double original number
	  * @return the float converted number
	  */
	 public float convertToFloat(double number) {  //pixels to millipoints
		 int resolution= Toolkit.getDefaultToolkit().getScreenResolution(); //dpi
		 double conversion=(number*72)/(resolution*1000);
		 return (float) conversion;
	 }
	 /** spinAroundAxis *******************************************
	  * spins the object around its own axis in 3d
	  * @param the multiplier value from the speed adjust scroll bar
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  * @return the RotationInterpolar of the spin
	  */
	 public RotationInterpolator spinAroundAxis (int multiplier, int divisor, double  sunRadius) {
		 Alpha axisAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,findAlphaSpin(multiplier, divisor,sunRadius),0, 0, 0, 0, 0);
		 Transform3D rotation = new Transform3D();
		 rotation.rotY(Math.PI/180*axialTilt); //make the axis
		 
		 RotationInterpolator spin;
		 if (directionRotates.equals("CW")) {
			 spin = new RotationInterpolator(axisAlpha, getTransformGroup(),rotation, 0.0f, (float) Math.PI * 2.0f);
		 }
		 else {  //clockwise
			 spin = new RotationInterpolator(axisAlpha, getTransformGroup(),rotation,(float) Math.PI * 2.0f, 0.0f);
		 }
		 transformAll.addChild(spin); // add it to the object's main transform group
		 return spin;
	 }
	 /** findAlphaSpin *******************************************
	  * finds the time it takes to make one revolution around itself
	  * @param the multiplier value from the speed adjust scroll bar
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  * @return the long time
	  */
	 private long findAlphaSpin(int multiplier, int divisor, double sunRadius) {
		 double speed=speedRotates*multiplier; //pixels a second			
		 double distanceAxis=2*Math.PI; //in pixels   
		 double time= distanceAxis/speed; //in seconds
		 return (long)time;
	 }
	 //--------------------------------------------abstract methods--------------------------------
	/** draw *******************************************
	  * draws the space object based on location parameters
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public abstract void draw(Graphics g, int backgroundX, int backgroundY, int divisor);
	/** renderSphere *******************************************
	  * creates a sphere object and adds it to its own personal transform group
	  * @param the divisor value from the size adjust scroll bar
	  */
	public abstract void renderSphere(int divisor);	
	/** startingPos *******************************************
	  * transforms the space object from the center to the starting position of its orbit
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public abstract void startingPos(int backgroundX, int backgroundY, int divisor);
} //end of SpaceObject class