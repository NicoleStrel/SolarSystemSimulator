//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

/** 
* [TextLabel.java]
* @author Nicole Streltsov
* A class that defines the text label next to the planet object
* January 2019
*/
class TextLabel{
	//variables
	OrbitalMove orbitalMovement;
	String name;
	double radius;
	double distanceFromSun;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int centerX=(int)(screenSize.getWidth()/2);	     
	private int centerY=(int)(screenSize.getHeight()/2)-100;
	
	/** TextLabel *******************************************
	  * constructor for the text label class
	  * @param the name of the planet
	  * @param the orbital movement object
	  * @param the radius of the planet
	  * @param the distance the planet is away from the sun
	  */
	TextLabel(String name,OrbitalMove orbitalMovement, double radius, double distanceFromSun){
		this.name=name;
		this.orbitalMovement=orbitalMovement;
		this.radius=radius;
		this. distanceFromSun= distanceFromSun;
	}
	/** draw *******************************************
	  * draws the text label just above the given planet
	  * @param graphics component
	  * @param integer background x coordinate 
	  * @param integer background y coordinate 
	  * @param the divisor value from the size adjust scroll bar
	  */
	public void draw (Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int startingX=(int)(centerX-(distanceFromSun/divisor)-(radius/divisor*2));
		 int startingY=(int)(centerY-radius/divisor);
		 g.setColor(Color.white);
		 g.drawString(name,startingX+backgroundX+orbitalMovement.getOrbitalX(), startingY+backgroundY+orbitalMovement.getOrbitalY());
	}
} //end of TextLabel class