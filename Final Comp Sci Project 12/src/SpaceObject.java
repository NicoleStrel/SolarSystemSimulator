abstract class SpaceObject{
	private double axialTilt; //degreees
	private double speedRotates; //pixels/s
	private String directionRotates;
	private double radius;
	private String name;
	SpaceObject(){}
	SpaceObject(String name, double speedRotates, double axialTilt, String directionRotates, double radius){
		this.name=name;
		this.speedRotates=speedRotates;
		this.axialTilt=axialTilt;
		this.directionRotates= directionRotates;
		this.radius=radius;
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
}