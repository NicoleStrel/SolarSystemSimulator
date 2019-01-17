class ClickArea {
	private SpaceObject object;
	private int  divisor;
	private int  backgroundX;
	private int backgroundY;
	
	ClickArea(SpaceObject object, int divisor, int  backgroundX, int backgroundY){ //planet
		this.object=object;
		this.divisor=divisor;
		this.backgroundX=backgroundX;
		this.backgroundY=backgroundY;
	}
	public boolean  contains(int cursorX, int cursorY) {
		int radius=(int)object.getRadius()/divisor;
		int x,y;
		if (object instanceof Planet) {
			int changesX=backgroundX+((Planet)object).getOrbitalMovement().getOrbitalX();
			int changesY=backgroundY+((Planet)object).getOrbitalMovement().getOrbitalY();
			x=object.getCenterX()-(int)((Planet)object).getDistanceFromSun()/divisor-radius*2+changesX; 
			y=object.getCenterY()-radius+changesY;
		}
		else {
			x=object.getCenterX()+ backgroundX;
			y=object.getCenterY()-radius+backgroundY;
		 }	
			//check x
			if ((cursorX>=x) && (cursorX<=x+radius*2)){
				//check y
				if ((cursorY>=y) && (cursorY<=y+radius*2)) {
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