import java.awt.Color;
import java.awt.Graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

/** 
* [Sun.java]
* @author Nicole Streltsov
* Defines the sun object in the solar system
* January 2019
*/
class Sun extends SpaceObject{
	/** Sun *******************************************
	  * constructor for the sun object class
	  * @param the name of the planet
	  * @param the speed it rotates around itself
	  * @param the angle of its tilt in degrees
	  * @param the String direction the planet rotates around itself
	  * @param the radius of the planet
	  * @param the color of the planet
	  * @param its array of data from the text file
	  */
	Sun(String name, double speedRotates, double angleOfTilt, String directionRotates, double radius, Color color, String [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
	}
	
	/** getSunTransform *******************************************
	  * gets the transformation of the sun (vector)
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  * @return the Transform3D transformation
	  */
	public Transform3D getSunTransform(int backgroundX, int backgroundY, int divisor) {
		 Transform3D t = new Transform3D();
		 double radius2=getRadius()/divisor;
		 Vector3d vector = new Vector3d(convertToFloat(centerX+radius2+backgroundX),convertToFloat(backgroundY) , 0.0f); //sun starting location
		 t.set(vector);
		 return t;
	}
	
	/** draw *******************************************
	  * draws the sun based on location parameters
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public void draw(Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int radius2=(int)(getRadius()/divisor); //divide by ratio
		 g.setColor(getColor());
		 g.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 	  
	}
	
	/** renderSphere *******************************************
	  * creates a sphere object and adds it to its own personal transform group
	  * @param the divisor value from the size adjust scroll bar
	  */
	 public void renderSphere(int divisor) { 	
		 double radius2=getRadius()/divisor;
		 
		 //apearance
		 Appearance app = new Appearance();			
		 Color3f eColor = new Color3f(0.0f, 0.0f, 0.0f);
		 Color3f sColor = new Color3f(1.0f, 1.0f, 1.0f);
		 Color3f objColor = new Color3f(1.0f, 1.0f, 0.0f);	 
		 Material m = new Material(objColor, eColor, objColor, sColor, 128.0f);
		 m.setLightingEnable(true);
		 		 
		 //add sphere
		 app.setMaterial(m);
		 Sphere sphere= new Sphere(convertToFloat(radius2), Sphere.GENERATE_NORMALS, 80,app); 
		 getInnerT().addChild(sphere); //add the sphere to its own inner transform group
	 }
	 
	 /** startingPos *******************************************
	  * transforms the sun from the center to its starting position 
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	 public void startingPos(int backgroundX, int backgroundY, int divisor) {	//using this for readability 	   
		    getInnerT().setTransform(getSunTransform(backgroundX,backgroundY,divisor)); //set and use other method
	 }
	 
	 /** addSpotLight *******************************************
	  * adds the lighting that the sun emits
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  * @return the Spotlight light
	  */
	 public SpotLight addSpotLight(int backgroundX, int backgroundY, int divisor) {
		 double radius2=getRadius()/divisor;
		 Color3f color = new Color3f(1.0f, 1.0f, 0.88f); //emit yellowish white
		 Point3f point= new  Point3f(convertToFloat(centerX+radius2+backgroundX),convertToFloat(backgroundY) , 0.0f); //its location
		 Vector3f vector = new Vector3f(convertToFloat(centerX+radius2+backgroundX), convertToFloat(backgroundY), 0.0f); //its location
		 Point3f atten = new Point3f(1.0f, 0.0f, 0.0f);
		 SpotLight light = new SpotLight(color,point, atten, vector,(float) Math.PI, 128.0f); //all around, brightest shine
		 getInnerT().addChild(light);
		 return light;
	 }
} //end of Sun class