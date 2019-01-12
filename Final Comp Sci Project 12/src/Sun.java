import java.awt.Color;
import java.awt.Graphics;

class Sun extends SpaceObject{
	
	Sun(String name, double daysRotates, double angleOfTilt, String directionRotates, double radius, Color color){
		super(name, daysRotates, angleOfTilt, directionRotates, radius, color);
	}
	public void drawSun(Graphics g,int centerX, int centerY, int backgroundX, int backgroundY, double ratioP) {
		 int radius2=(int)(radius/ratioP);
		  g.setColor(color);
		  g.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 
	}
	
}