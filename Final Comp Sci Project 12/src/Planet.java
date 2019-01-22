//imports
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Sphere;

/** 
* [Planet.java]
* @author Nicole Streltsov
* Defines the planet object in the solar system
* January 2019
*/
class Planet extends SpaceObject{
	private double distanceFromSun; //pixels
	private double speedRotateAroundSun; //pixels/second
	private OrbitalMove orbitalMovement;
    
	/** Planet *******************************************
	  * constructor for the planet object class
	  * @param the name of the planet
	  * @param the speed it rotates around itself
	  * @param the angle of its tilt in degrees
	  * @param the String direction the planet rotates around itself
	  * @param the radius of the planet
	  * @param the color of the planet
	  * @param the its distance away from the sun in pixels
	  * @param the speed it rotates around the sun
	  * @param the orbital movement default start
	  * @param its array of data from the text file
	  */
	Planet(String name, double speedRotates, double angleOfTilt, String directionRotates,double radius, Color color, double distanceFromSun, double speedRotateAroundSun, OrbitalMove orbitalMovement, String  [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
		this.distanceFromSun=distanceFromSun;
		this.speedRotateAroundSun=speedRotateAroundSun;		
		this.orbitalMovement=orbitalMovement;
	}
	
	/** getDistanceFromSun *******************************************
	  * getter for distance from sun variable
	  * @return the double value for distanceFromSun
	  */
	public double getDistanceFromSun() {
		return this.distanceFromSun;
	}
	
	/** getSpeedRotateSun *******************************************
	  * getter for speed around sun variable
	  * @return the double value for speedRotateAroundSun
	  */
	public double getSpeedRotateSun() {
		return this.speedRotateAroundSun;	
	}
	
	/** getOrbitalMovement *******************************************
	  * getter for orbital move object
	  * @return the orbital move object 
	  */
	public OrbitalMove getOrbitalMovement() {
		return this.orbitalMovement;
	}
	
	/** draw*******************************************
	  * draws the planet in 2d based on given location parameters
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public void draw(Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int startingPoint=(int)(centerX-(distanceFromSun/divisor));
		 int radius2=(int)(getRadius()/divisor);
	     g.setColor(getColor());
		 g.fillOval(startingPoint-radius2*2+backgroundX+orbitalMovement.getOrbitalX(),centerY-radius2+backgroundY+orbitalMovement.getOrbitalY(),radius2*2, radius2*2);
	}
	
	/** drawOrbit *******************************************
	  * draws the orbit of the planet in 2d based on given location parameters
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  */
	public void drawOrbit(Graphics g, int backgroundX, int backgroundY,int divisor, int sunRadius) {
		int distance=(int)(distanceFromSun/divisor);
		int planetRadius=(int)(getRadius()/divisor);
		int radius2=distance+sunRadius/divisor+planetRadius; //calulate the radius of the orbit itself
	    g.setColor(Color.white);
	    g.drawOval(centerX+backgroundX-distance-planetRadius,centerY+backgroundY-radius2, radius2*2, radius2*2); 
		
	}
	
	/** moveOrbital *******************************************
	  * moves the planet around its orbit based on location parameters and current radian
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  */
	public void moveOrbital(int divisor, double sunRadius) {
		 double radius2=(distanceFromSun+sunRadius+getRadius())/divisor;		
		 double radian=orbitalMovement.getRadian();
		 
		 int newX=(int)(radius2-radius2*Math.cos(radian));	//travel around circle x
		 int newY=(int)(radius2*Math.sin(radian)); //travel around circle y
		
		 //set the new shift values
		 orbitalMovement.setOrbitalX(newX);
		 orbitalMovement.setOrbitalY(newY);		 
	 }
	
	/** drawAxis *******************************************
	  * draws the axis of the planet in 2d based on given location parameters
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public void drawAxis(Graphics g, int divisor, int backgroundX, int backgroundY) {
		//declare variables
		int startingPoint=(int)(centerX-(distanceFromSun/divisor));
		int radius2=(int)(getRadius()/divisor);
		int beyondVertical;
		int x1=0, x2=0, y1=0, y2=0;
		
		//depending on the quadrant the tilt is located, assign values for x's  and y's 
		//note: working with values in the clockwise direction (usually unit circle is counter-clockwise)
		//Q1
		if (getAngle()<90) {
			beyondVertical=radius2*(int)(Math.tan(getAngle()*(Math.PI/180))); 
			x1=startingPoint-radius2+beyondVertical;
			y1=centerY-radius2-5;
			x2=startingPoint-radius2-beyondVertical;
			y2=centerY+radius2+5;
		}
		//Q4
		else if (getAngle()>90  && getAngle()<180) {
			beyondVertical=radius2*(int)(Math.tan((180-getAngle())*(Math.PI/180))); 
			x1=startingPoint-radius2+beyondVertical;
			y1=centerY+radius2+5;
			x2=startingPoint-radius2-beyondVertical;
			y2=centerY-radius2-5;
		}
		//Q3
		else if (getAngle()>180 && getAngle()<270) {
			beyondVertical=radius2*(int)(Math.tan((getAngle()-180)*(Math.PI/180))); 
			x1=startingPoint-radius2-beyondVertical;
			y1=centerY+radius2+5;
			x2=startingPoint-radius2+beyondVertical;
			y2=centerY-radius2-5;
		}
		//Q2
		else if (getAngle()>270) {
			beyondVertical=radius2*(int)(Math.tan((360-getAngle())*(Math.PI/180))); 
			x1=startingPoint-radius2-beyondVertical;
			y1=centerY-radius2-5;
			x2=startingPoint-radius2+beyondVertical;
			y2=centerY+radius2+5;
		}
		g.setColor(Color.white);
		g.drawLine(x1+backgroundX+orbitalMovement.getOrbitalX(), y1+backgroundY+orbitalMovement.getOrbitalY(), x2+backgroundX+orbitalMovement.getOrbitalX(), y2+backgroundY+orbitalMovement.getOrbitalY());
	}
	
	/** renderSphere *******************************************
	  * creates a sphere object and adds it to its own personal transform group
	  * @param the divisor value from the size adjust scroll bar
	  */
	 public void renderSphere(int divisor) { 	
		 double radius2=getRadius()/divisor;
		 
		 //set apearance
		 Appearance app = new Appearance();	
		 Color3f eColor = new Color3f(0.0f, 0.0f, 0.0f);
		 Color3f sColor = new Color3f(1.0f, 1.0f, 1.0f);
		 Color3f objColor = null;
		 try {
			objColor = readPlanetColor();
		 } catch (FileNotFoundException e) {
		 }
		 Material m = new Material(objColor, eColor, objColor, sColor, 100.0f);
		 m.setLightingEnable(true);
		 
		 //generate sphere
		 app.setMaterial(m);
		 Sphere sphere= new Sphere(convertToFloat(radius2), Sphere.GENERATE_NORMALS, 120,app); 
		 getInnerT().addChild(sphere);
	 }
	 
	 /** startingPos *******************************************
	  * transforms the planet from the center to the starting position of its orbit
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	 public void startingPos(int backgroundX, int backgroundY, int divisor) {
		    Transform3D t = new Transform3D();
		    double radius2=getRadius()/divisor;
		    double distance =distanceFromSun/divisor;
		    Vector3d lPos1 = new Vector3d(convertToFloat(centerX-radius2-distance+backgroundX), convertToFloat(backgroundY) , 0.0f);
		    t.set(lPos1);
		    getInnerT().setTransform(t); //add to its own transform group
	 }
	 
	 /** addOrbital *******************************************
	  * rotates the sphere with the rotation interpolar
	  * @param  the sun's tranformation (location)
	  * @param the multiplier value from the speed adjust scroll bar
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  * @return the rotation interpolar for the orbit rotation
	  */
	 public RotationInterpolator addOrbital(Transform3D sunTransform, int multiplier, int divisor, double sunRadius) {
	    // defalt for now (rotate around center)
	    Alpha rotorAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,findAlphaDuration(multiplier, divisor,sunRadius),0, 0, 0, 0, 0);
	    RotationInterpolator rotator = new RotationInterpolator(rotorAlpha,getTransformGroup(),sunTransform, 0.0f, (float) Math.PI * 2.0f);  
	    getTransformGroup().addChild(rotator); //add to its own transform group
	    return rotator;
	 }
	 
	 /** findAlphaDuration *******************************************
	  * finds the alpha value of the duration it takes to complete one full orbit
	  * @param the multiplier value from the speed adjust scroll bar
	  * @param the divisor value from the size adjust scroll bar
	  * @param the sun's radius in pixels
	  * @return the long alpha duration in ms
	  */
	 private long findAlphaDuration(int multiplier, int divisor, double sunRadius) { //in milliseconds
		 double speed=speedRotateAroundSun*multiplier*10; //pixels a second		
		 double radius2=getRadius()/divisor;		
		 double distance= distanceFromSun/divisor;		
		 double distanceOrbit=2*Math.PI*(radius2+distance+sunRadius); //in pixels
		 double time= distanceOrbit/speed; //in seconds
		 return (long)time;//in milliseconds
	 }
	 
	 /** readPlanetColor *******************************************
	  * reads the planet's floating point percent color from the file 
	  * @return the color3f value
	  * @throws FileNotFoundException
	  */
	 private Color3f readPlanetColor() throws FileNotFoundException{
		 File fileIn = new File("planetColors3D.txt");
		 Scanner textIn = new Scanner(fileIn); 
		 Color3f planetColor=null;
		 
		 //iterate through
		 while(textIn.hasNext()){
			 String line= textIn.nextLine();
			 String [] data= line.split("[|]");
			 if (data[0].equals(getName())){ //assign to the proper planet
				 String [] values =data[1].split(","); 
				 planetColor=new Color3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));     
			 }
		 }
		 textIn.close();
		 return planetColor;
	 }
} //end of Planet class