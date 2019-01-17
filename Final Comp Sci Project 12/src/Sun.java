import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TexturePaint;
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
	public void drawSun(Graphics g2, int backgroundX, int backgroundY, int divisor) {
		 int radius2=(int)(radius/divisor);
		  
		 g2.setColor(color);
		 g2.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 
		
		 /*
		 Graphics2D g2d = bi.createGraphics();
		 Image sunTexture = null;
		 try {sunTexture = ImageIO.read(new File("images/sunTexture.jpg"));} catch (IOException e) {}
		 TexturePaint tp = new TexturePaint();
		    // Now fill the round rectangle.
		    g2.setPaint(tp)
		    */
		 //with texture
		 /*
		
		 //Image sunResized = sunTexture.getScaledInstance(4000/divisor, 2000/divisor, Image.SCALE_DEFAULT);
		 
		  g.setClip(new Ellipse2D.Float(centerX+backgroundX, centerY-radius2+backgroundY, radius2*2, radius2*2));   
		  g.drawImage(sunTexture,centerX+backgroundX, centerY-radius2+backgroundY, null);
		
		*/
		  
	}
	public Sphere getSphere() {
		return this.sphere;
	}
	public void drawStats(Graphics g) {
		
	}	
}