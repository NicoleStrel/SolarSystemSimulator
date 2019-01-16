class ClickArea {
	private int x;
	private int y;
	private double radius;
	private String type;
	
	ClickArea(OrbitalMove orbitalMovement, double radius, String type){ //planet
		this.x=orbitalMovement.getOrbitalX()- (int)radius;
		this.y=orbitalMovement.getOrbitalY()- (int)radius;
		this.radius=radius;
		this.type=type;
	}
	ClickArea (int x, int y, double radius, String type){ //sun 
		this.x=x;
		this.y=y-(int)radius;
		this.radius=radius;
		this.type=type;
	}
	public boolean  contains(int x, int y) {
		//check x
		if ((x>=this.x) && (x<=this.x+radius*2)){
			//check y
			if ((y>=this.y) && (y<= this.y+radius*2)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}