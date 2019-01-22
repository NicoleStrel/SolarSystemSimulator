/** 
* [ClickArea.java]
* @author Nicole Streltsov
* Calculates the area in which the space object is, in order to detected it via mouse click
* January 2019
*/
class ClickArea {
	private SpaceObject object;
	private int  divisor;
	private int  backgroundX;
	private int backgroundY;

    /** ClickArea *******************************************
	  * constructor for the  click area class
	  * @param the space object
	  * @param the size adjustment divisor value
	  * @param the background x translation
	  * @param the background y translation
	  */
	ClickArea(SpaceObject object, int divisor, int  backgroundX, int backgroundY){ //planet
		this.object=object;
		this.divisor=divisor;
		this.backgroundX=backgroundX;
		this.backgroundY=backgroundY;
	}
	
	/** contains *******************************************
      * checks  if the cursor is in the click area of the space object
      * @param the cursor x coordinate
      * @param the cursor y coordinate
      * @return a boolean value; true if the cursor is in the click area, false if its not
      */
	public boolean  contains(int cursorX, int cursorY) {
		int radius=(int)object.getRadius()/divisor;
		int x,y, endX=0, endY=0;
		//if planet, make its surounding  click box
		if (object instanceof Planet) {
			int changesX=backgroundX+((Planet)object).getOrbitalMovement().getOrbitalX();
			int changesY=backgroundY+((Planet)object).getOrbitalMovement().getOrbitalY();
			x=object.getCenterX()-(int)((Planet)object).getDistanceFromSun()/divisor-radius*2+changesX; 
			y=object.getCenterY()-radius+changesY;
			endX=x+radius*2;
			endY=y+radius*2;
		}
		//if sun, make its inner box (since the  sun is  huge)
		else {
			int shift=(int)((2*radius-Math.sqrt(2)*radius)/2); //calculate the max inner box shift
			x=object.getCenterX()+ backgroundX+shift;
			y=object.getCenterY()-radius+backgroundY+shift;
			endX=object.getCenterX()+ backgroundX+radius*2-shift;
			endY=object.getCenterY()+backgroundY+radius-shift;
		 }	
		
		//Go through the click area for both x  and y
		//check x
		if ((cursorX>=x) && (cursorX<=endX)){
			//check y
			if ((cursorY>=y) && (cursorY<=endY)) {
				return true; //return true  ifcorrect
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