import java.util.Iterator;

class OrbitalMove{
	private int orbitalX; //movement relative to center of circle
	private int orbitalY;
	private double radian;
	
	OrbitalMove(int orbitalX, int orbitalY){
		this.orbitalX=orbitalX;
		this.orbitalY=orbitalY;
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
	public double getRadian() {
		return this.radian;
	}
	public void setRadian(double radian) {
		this.radian=radian;
	}
	public void updateRadialMovement(Planet planet, int multiplier, double elapsedTime) {
		double speed=planet.getSpeedRotateSun()*multiplier;
		double radian2=elapsedTime*speed+radian;	
		radian=radian2;		 			 

	}
	
}