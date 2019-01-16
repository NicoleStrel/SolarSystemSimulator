import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

class TextLabel{
	OrbitalMove orbitalMovement;
	String name;
	double radius;
	double distanceFromSun;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int centerX=(int)(screenSize.getWidth()/2);	     
	private int centerY=(int)(screenSize.getHeight()/2)-100;
	
	TextLabel(String name,OrbitalMove orbitalMovement, double radius, double distanceFromSun){
		this.name=name;
		this.orbitalMovement=orbitalMovement;
		this.radius=radius;
		this. distanceFromSun= distanceFromSun;
	}
	public void draw (Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int startingX=(int)(centerX-(distanceFromSun/divisor)-(radius/divisor*2));
		 int startingY=(int)(centerY-radius/divisor);
		 g.setColor(Color.white);
		 g.drawString(name,startingX+backgroundX+orbitalMovement.getOrbitalX(), startingY+backgroundY+orbitalMovement.getOrbitalY());
		 
		 
	}
}