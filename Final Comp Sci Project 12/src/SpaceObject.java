import java.awt.Color;

abstract class SpaceObject{
	private double axialTilt; //degreees
	private double speedRotates; //pixels/s
	private String directionRotates;
	private double radius;
	private Color color;
	private String name;
	
	SpaceObject(){}
	SpaceObject(String name, double speedRotates, double axialTilt, String directionRotates, double radius, Color color){
		this.name=name;
		this.speedRotates=speedRotates;
		this.axialTilt=axialTilt;
		this.directionRotates= directionRotates;
		this.radius=radius;
		this.color=color;
	}
	public void roateAroundAxis() {
		
	}
	public void covertSpeedRotation() {
		
	}
	public double getAngle() {
		return this.axialTilt;
	}
	public void setAngle(double axialTilt) {
		this.axialTilt=axialTilt;
	}
	public double getDaysRotates() {
		return this.speedRotates;
	}
	public void setDaysRotates(double daysRotates){
		this.speedRotates=daysRotates;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getDirection() {
		return this.directionRotates;
	}
	public void setDirection(String directionRotates) {
		this.directionRotates=directionRotates;
	}
	public double getRadius() {
		return this.radius;
	}
	public void setRadius(double radius) {
		this.radius=radius;
	}
	public Color getColor() {
		return this.color;
	}
	public void setColor(Color color) {
		this.color=color;
	}
}