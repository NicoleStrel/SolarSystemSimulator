import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.xml.crypto.dsig.Transform;

import com.sun.j3d.utils.geometry.Sphere;

class Sun extends SpaceObject{
	Sun(String name, double speedRotates, double angleOfTilt, String directionRotates, double radius, Color color, String [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
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
	 public TransformGroup renderSphere(int backgroundX, int backgroundY, int divisor) { 	
		 double radius2=radius/divisor;
		 
		 //apearance
		 Appearance app = new Appearance();	
		 Color3f ambientColor=new  Color3f(0.0f, 0.0f, 0.0f);   
		 Color3f emissiveColor=new Color3f(0.0f, 0.5f, 1.0f);
		 Color3f diffuseColor=new Color3f(0.0f, 0.0f, 1.0f);           // do colors later in a text file
		 Color3f specularColor=new Color3f(1.0f, 1.0f, 1.0f);
		 Material material=new Material(ambientColor,emissiveColor,diffuseColor,specularColor,100.0f);
		 ColoringAttributes color= new ColoringAttributes(new  Color3f(0.0f, 0.0f, 1.0f),ColoringAttributes.NICEST);
		 
		 //add sphere
		 app.setMaterial(material);
		 app.setColoringAttributes(color);
		 Sphere sphere= new Sphere(convertToFloat(radius2), Sphere.GENERATE_NORMALS, 120,app); 
		 
		 //transform
		 Transform3D transform= new Transform3D();
		 Vector3f vector = new Vector3f(convertToFloat(centerX+radius2+backgroundX), 0.0f , 0.0f);   
		 transform.set(vector);
		 TransformGroup tg = new TransformGroup(transform);
		 tg.addChild(sphere);
		 return tg;
	 }
}