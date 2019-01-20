import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

class Planet extends SpaceObject{
	protected double distanceFromSun; //pixels
	private double speedRotateAroundSun; //pixels/s
	private OrbitalMove orbitalMovement;
	
	Planet(String name, double speedRotates, double angleOfTilt, String directionRotates,double radius, Color color, double distanceFromSun, double speedRotateAroundSun, OrbitalMove orbitalMovement, String  [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
		this.distanceFromSun=distanceFromSun;
		this.speedRotateAroundSun=speedRotateAroundSun;		
		this.orbitalMovement=orbitalMovement;
	}
	public void rotateAroundOrbit(double distance, int days) {
		
	}
	public double getDistanceFromSun() {
		return this.distanceFromSun;
	}
	public void setDistanceFromSun(double distance) {
		this.distanceFromSun=distance;
	}
	public double getSpeedRotateSun() {
		return this.speedRotateAroundSun;	
	}
	public void setSpeedRotateSun(double speedRotates) {
		this.speedRotateAroundSun=speedRotates;
	}
	public OrbitalMove getOrbitalMovement() {
		return this.orbitalMovement;
	}
	public void drawPlanet(Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int startingPoint=(int)(centerX-(distanceFromSun/divisor));
		 int radius2=(int)(radius/divisor);
	     g.setColor(color);
		 g.fillOval(startingPoint-radius2*2+backgroundX+orbitalMovement.getOrbitalX(),centerY-radius2+backgroundY+orbitalMovement.getOrbitalY(),radius2*2, radius2*2);
	}
	public void drawOrbit(Graphics g, int backgroundX, int backgroundY,int divisor, int sunRadius) {
		int distance=(int)(distanceFromSun/divisor);
		int planetRadius=(int)(radius/divisor);
		int radius2=distance+sunRadius/divisor+planetRadius;
	    g.setColor(Color.white);
	    g.drawOval(centerX+backgroundX-distance-planetRadius,centerY+backgroundY-radius2, radius2*2, radius2*2); 
		
	}
	 public void moveOrbital(int divisor, double sunRadius) {
		 double radius2=(distanceFromSun+sunRadius+radius)/divisor;		
		 double radian=orbitalMovement.getRadian();
		 
		 int newX=(int)(radius2-radius2*Math.cos(radian));	
		 int newY=(int)(radius2*Math.sin(radian));
		 
		 orbitalMovement.setOrbitalX(newX);
		 orbitalMovement.setOrbitalY(newY);		 
	 }
	public void drawAxis(Graphics g, int divisor, int backgroundX, int backgroundY) {
		int startingPoint=(int)(centerX-(distanceFromSun/divisor));
		int radius2=(int)(radius/divisor);
		int beyondVertical;
		int x1=0, x2=0, y1=0, y2=0;
		//depending on the quadrant the tilt is located, assign values for x's  and y's 
		//note: working with values in the clockwise direction (usually unit circle is counter-clockwise)
		//Q1
		if (axialTilt<90) {
			beyondVertical=radius2*(int)(Math.tan(axialTilt*(Math.PI/180))); 
			x1=startingPoint-radius2+beyondVertical;
			y1=centerY-radius2-5;
			x2=startingPoint-radius2-beyondVertical;
			y2=centerY+radius2+5;
		}
		//Q4
		else if (axialTilt>90  && axialTilt<180) {
			beyondVertical=radius2*(int)(Math.tan((180-axialTilt)*(Math.PI/180))); 
			x1=startingPoint-radius2+beyondVertical;
			y1=centerY+radius2+5;
			x2=startingPoint-radius2-beyondVertical;
			y2=centerY-radius2-5;
		}
		//Q3
		else if (axialTilt>180 && axialTilt<270) {
			beyondVertical=radius2*(int)(Math.tan((axialTilt-180)*(Math.PI/180))); 
			x1=startingPoint-radius2-beyondVertical;
			y1=centerY+radius2+5;
			x2=startingPoint-radius2+beyondVertical;
			y2=centerY-radius2-5;
		}
		//Q2
		else if (axialTilt>270) {
			beyondVertical=radius2*(int)(Math.tan((360-axialTilt)*(Math.PI/180))); 
			x1=startingPoint-radius2-beyondVertical;
			y1=centerY-radius2-5;
			x2=startingPoint-radius2+beyondVertical;
			y2=centerY+radius2+5;
		}
		g.setColor(Color.white);
		g.drawLine(x1+backgroundX+orbitalMovement.getOrbitalX(), y1+backgroundY+orbitalMovement.getOrbitalY(), x2+backgroundX+orbitalMovement.getOrbitalX(), y2+backgroundY+orbitalMovement.getOrbitalY());
	}
	
	 public TransformGroup renderSphere(int backgroundX, int backgroundY, int divisor) { 	
		 double radius2=radius/divisor;
		 
		 //apearance
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
		 
		 //add sphere
		 app.setMaterial(m);
		 Sphere sphere= new Sphere(convertToFloat(radius2), Sphere.GENERATE_NORMALS, 120,app); 
		 
		 //transform
		 double distance =distanceFromSun/divisor;
		 //System.out.println (getName()+"   -   " +distance);
		 Transform3D transform= new Transform3D();
		 Vector3f vector = new Vector3f(convertToFloat(centerX-radius2-distance+backgroundX), 0.0f , 0.0f);   
		 transform.set(vector);
		 TransformGroup tg = new TransformGroup(transform);
		 tg.addChild(sphere);
		 return tg;
	 }
	 private Color3f readPlanetColor() throws FileNotFoundException{
		 File fileIn = new File("planetColors3D.txt");
		 Scanner textIn = new Scanner(fileIn); 
		 Color3f planetColor=null;
		 
		 while(textIn.hasNext()){
			 String line= textIn.nextLine();
			 String [] data= line.split("[|]");
			 if (data[0].equals(getName())){
				 String [] values =data[1].split(",");
				 planetColor=new Color3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));     
			 }
		 }
		 textIn.close();
		 return planetColor;
	 }

}