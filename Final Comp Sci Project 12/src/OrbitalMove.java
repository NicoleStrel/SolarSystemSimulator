/** 
* [OrbitalMove.java]
*  @author Nicole Streltsov
*  Moves the 2D planet around its orbit by translation values
* January 2019
*/
class OrbitalMove{
	private int orbitalX; //movement relative to center of circle
	private int orbitalY;
	private double radian;
	
	/** OrbitalMove *******************************************
	  * constructor for the orbital move class
	  * @param the orbital x value
	  *  @param the orbital y value
	  */
	OrbitalMove(int orbitalX, int orbitalY){
		this.orbitalX=orbitalX;
		this.orbitalY=orbitalY;
	}
	/** getOrbitalX *******************************************
	  * getter for orbitalX
	  * @return the integer value for orbitalX
	  */
	public int getOrbitalX() {
		return this.orbitalX;
	}
	/** setOrbitalX *******************************************
	  * setter for orbitalX
	  * @param the integer value for orbitalX
	  */
	public void setOrbitalX(int orbitalX) {
		this.orbitalX=orbitalX;
	}
	/** getOrbitalY *******************************************
	  * getter for orbitalY
	  * @return the integer value for orbitalY
	  */
	public int getOrbitalY() {
		return this.orbitalY;
	}
	/** setOrbitalY *******************************************
	  * setter for orbitalY
	  * @param the integer value for orbitalY
	  */
	public void setOrbitalY(int orbitalY) {
		this.orbitalY=orbitalY;
	}
	/** getRadian *******************************************
	  * getter for the radian angle
	  * @return the double value of the current radian angle
	  */
	public double getRadian() {
		return this.radian;
	}
	/** updateRadialMovement *******************************************
	  * updates the radian angle based on elapsed time and the speed of the planet
	  * @param the planet object
	  * @param the multiplier speed value(from scroll bar)
	  * @param the elapsed time in seconds
	  */
	public void updateRadialMovement(Planet planet, int multiplier, double elapsedTime) {
		double speed=planet.getSpeedRotateSun()*multiplier;
		double radian2=elapsedTime*speed+radian;	
		radian=radian2;		 			 
	}
} //end of OrbitalMove class