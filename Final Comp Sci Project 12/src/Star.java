//imports
import java.awt.Color;
import java.awt.Graphics;

/** 
* [Star.java]
* @author Nicole Streltsov
* Defines the star coordinates and drawing
* January 2019
*/
class Star{
	//variables
	private int x;
	private int y;
	
	/** Star *******************************************
	  * constructor for the star class
	  * @param the star's x value
	  * @param the star's y value
	  */
	Star (int x, int y){
		this.x=x;
		this.y=y;
	}
	
	/** draw *******************************************
	  * draws the star
	  * @param the graphics component
	  */
	public void draw(Graphics g) {
		 g.setColor(Color.white);
		 g.fillOval(x, y, 2,2);
	}
} //end of Star class