import java.awt.Color;

class Planet extends SpaceObject{
	private double distanceFromSun; //pixels
	private double speedRotateAroundSun; //pixels/s
	private int orbitalX; //movement relative to center of circle
	private int orbitalY;
	
	Planet(){}
	Planet(String name, double daysRotates, double angleOfTilt, String directionRotates,double radius, Color color, double distanceFromSun, double speedRotateAroundSun, int orbitalX, int orbitalY){
		super(name, daysRotates, angleOfTilt, directionRotates, radius, color);
		this.distanceFromSun=distanceFromSun;
		this.speedRotateAroundSun=speedRotateAroundSun;		
		this.orbitalX=orbitalX;
		this.orbitalY=orbitalY;
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
	public int getOrbitalX() {
		return this.orbitalX;
	}
	public void setOrbitalX(int orbitalX) {
		this.orbitalX=orbitalX;
	}
	public int getOrbitalY() {
		return this.orbitalY;
	}
	public void setOrbitalY(int orbitalY) {
		this.orbitalY=orbitalY;
	}
}