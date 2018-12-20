class Planet extends SpaceObject{
	private double distanceFromSun;
	private double daysRotateAroundSun;
	
	Planet(){}
	Planet(String name, double daysRotates, double angleOfTilt, double distanceFromSun, double daysRotateAroundSun){
		super(name, daysRotates, angleOfTilt);
		this.distanceFromSun=distanceFromSun;
		this.daysRotateAroundSun=daysRotateAroundSun;		
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
		return this.daysRotateAroundSun;	
	}
	public void setDaysRotateSu(double daysRotates) {
		this.daysRotateAroundSun=daysRotates;
	}
}