import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Sun extends SpaceObject{
	Sphere sphere;
	Sun(String name, double speedRotates, double angleOfTilt, String directionRotates, double radius, Color color, String [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
		this.sphere=new Sphere(radius);
	}
	public void drawSun(Graphics g, int backgroundX, int backgroundY, int divisor) {
		 int radius2=(int)(radius/divisor);
		  
		 g.setColor(color);
		 g.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 
		//with texture
		 
		 /*
		 Image sunTexture = null;
		 try {sunTexture = ImageIO.read(new File("images/sunTexture.jpg"));} catch (IOException e) {}
		 Image sunResized = sunTexture.getScaledInstance(4000/divisor, 2000/divisor, Image.SCALE_DEFAULT);
		 
		  g.setClip(new Ellipse2D.Float(centerX+backgroundX, centerY-radius2+backgroundY, radius2*2, radius2*2));   
		  g.drawImage(sunResized,centerX+backgroundX, centerY-radius2+backgroundY, null);
		  */
		  
	}
	public Sphere getSphere() {
		return this.sphere;
	}
	public void drawStats(Graphics g) {
		
	}	
}