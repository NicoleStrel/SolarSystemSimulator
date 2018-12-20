abstract class SpaceObject{
	private double angleOfTilt;
	private double daysRotates;
	private String name;
	SpaceObject(){}
	SpaceObject(String name, double daysRotates, double angleOfTilt){
		this.name=name;
		this.daysRotates=daysRotates;
		this.angleOfTilt=angleOfTilt;
	}
	public void roateAroundAxis() {
		
	}
	public void covertSpeedRotation() {
		
	}
	public double getAngle() {
		return this.angleOfTilt;
	}
	public void setAngle(double angleOfTilt) {
		this.angleOfTilt=angleOfTilt;
	}
	public double getDaysRotates() {
		return this.daysRotates;
	}
	public void setDaysRotates(double daysRotates){
		this.daysRotates=daysRotates;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
}