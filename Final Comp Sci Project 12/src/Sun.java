import java.awt.Color;
import java.awt.Graphics;

class Sun extends SpaceObject{
	Sphere sphere;
	Sun(String name, double speedRotates, double angleOfTilt, String directionRotates, double radius, Color color){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color);
		this.sphere=new Sphere(radius);
	}
	public void drawSun(Graphics g,int centerX, int centerY, int backgroundX, int backgroundY, int divisor) {
		 int radius2=(int)(radius/divisor);
		  g.setColor(color);
		  g.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 
	}
	public Sphere getSphere() {
		return this.sphere;
	}
	
}