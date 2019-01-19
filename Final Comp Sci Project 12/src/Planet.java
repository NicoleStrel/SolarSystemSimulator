import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;

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
}