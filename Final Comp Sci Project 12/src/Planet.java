class Planet extends SpaceObject{
	private double distanceFromSun; //pixels
	private double speedRotateAroundSun; //pixels/s
	
	Planet(){}
	Planet(String name, double daysRotates, double angleOfTilt, String directionRotates,double radius, double distanceFromSun, double speedRotateAroundSun){
		super(name, daysRotates, angleOfTilt, directionRotates, radius);
		this.distanceFromSun=distanceFromSun;
		this.speedRotateAroundSun=speedRotateAroundSun;		
	}
	public void rotateAroundOrbit(double distance, int days) {
		
	}
	public double getDistanceFromSun() {
		return this.distanceFromSun;
	}
	public void setDistanceFromSun(double distance) {
		this.distanceFromSun=distance;
	}
	public double getDaysRotateSun() {
		return this.speedRotateAroundSun;	
	}
	public void setDaysRotateSu(double speedRotates) {
		this.speedRotateAroundSun=speedRotates;
	}
}