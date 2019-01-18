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
		int x,y, endX=0, endY=0;
		if (object instanceof Planet) {
			int changesX=backgroundX+((Planet)object).getOrbitalMovement().getOrbitalX();
			int changesY=backgroundY+((Planet)object).getOrbitalMovement().getOrbitalY();
			x=object.getCenterX()-(int)((Planet)object).getDistanceFromSun()/divisor-radius*2+changesX; 
			y=object.getCenterY()-radius+changesY;
			endX=x+radius*2;
			endY=y+radius*2;
		}
		else {
			int shift=radius-(radius*(int)Math.cos(45*(Math.PI/180)));
			System.out.println(shift);
			x=object.getCenterX()+ backgroundX+shift;
			y=object.getCenterY()-radius+backgroundY+shift;
			endX=object.getCenterX()+ backgroundX+radius*2-shift;
			endY=object.getCenterY()+backgroundY+radius-shift;
		 }	
			//check x
			if ((cursorX>=x) && (cursorX<=endX)){
				//check y
				if ((cursorY>=y) && (cursorY<=endY)) {
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