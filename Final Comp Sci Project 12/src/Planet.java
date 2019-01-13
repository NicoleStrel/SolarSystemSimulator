import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;

class Planet extends SpaceObject{
	protected double distanceFromSun; //pixels
	private double speedRotateAroundSun; //pixels/s
	private OrbitalMove orbitalMovement;
	
	Planet(String name, double speedRotates, double angleOfTilt, String directionRotates,double radius, Color color, double distanceFromSun, double speedRotateAroundSun, OrbitalMove orbitalMovement){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color);
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
	public void setOrbitalX(OrbitalMove orbitalMovement) {
		this.orbitalMovement=orbitalMovement;
	}
	public void drawPlanet(Graphics g, int centerX, int centerY, int backgroundX, int backgroundY, int divisor) {
		 int startingPoint=(int)(centerX-(distanceFromSun/divisor));
		 int radius2=(int)(radius/divisor);
	     g.setColor(color);
		 g.fillOval(startingPoint-radius2*2+backgroundX+orbitalMovement.getOrbitalX(),centerY-radius2+backgroundY+orbitalMovement.getOrbitalY(),radius2*2, radius2*2);
		
	}
	public void drawOrbit(Graphics g, int centerX, int centerY, int backgroundX, int backgroundY,int divisor, int sunRadius) {
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
}