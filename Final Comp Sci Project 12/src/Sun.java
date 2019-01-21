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
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.xml.crypto.dsig.Transform;

import com.sun.j3d.utils.geometry.Sphere;

class Sun extends SpaceObject{
	Sun(String name, double speedRotates, double angleOfTilt, String directionRotates, double radius, Color color, String [] data){
		super(name, speedRotates, angleOfTilt, directionRotates, radius, color, data);
	}
	public void addChildT(RotationInterpolator rotator) {
		getTransformGroup().addChild(rotator);
	}
	public void drawSun(Graphics g2, int backgroundX, int backgroundY, int divisor) {
		 int radius2=(int)(radius/divisor);
		  
		 g2.setColor(color);
		 g2.fillOval(centerX+backgroundX,centerY-radius2+backgroundY,radius2*2, radius2*2);		 
		  
	}
	 public void renderSphere(int backgroundX, int backgroundY, int divisor) { 	
		 double radius2=radius/divisor;
		 
		 //apearance
		 Appearance app = new Appearance();			
		 Color3f eColor = new Color3f(0.0f, 0.0f, 0.0f);
		 Color3f sColor = new Color3f(1.0f, 1.0f, 1.0f);
		 Color3f objColor = new Color3f(1.0f, 1.0f, 0.0f);	 
		 Material m = new Material(objColor, eColor, objColor, sColor, 100.0f);
		 m.setLightingEnable(true);
		 ColoringAttributes colorAttribute= new ColoringAttributes(objColor, ColoringAttributes.NICEST);
		 		 
		 //add sphere
		 //app.setMaterial(m);
		 app.setColoringAttributes(colorAttribute);
		 Sphere sphere= new Sphere(convertToFloat(radius2), Sphere.GENERATE_NORMALS, 80,app); 
		 getInnerT().addChild(sphere);
	 }
	 public void startingPos(int backgroundX, int backgroundY, int divisor) {	 
		    Transform3D t = new Transform3D();
		    double radius2=radius/divisor;
		    Vector3d vector = new Vector3d(convertToFloat(centerX+radius2+backgroundX), backgroundY , 0.0f);
		    t.set(vector);
		    getInnerT().setTransform(t);
	 }
	 public SpotLight addSpotLight(int backgroundX, int backgroundY, int divisor) {
		 double radius2=radius/divisor;
		 Color3f color = new Color3f(1.0f, 1.0f, 0.88f);
		 Point3f point= new  Point3f(convertToFloat(centerX+radius2+backgroundX),backgroundY , 0.0f);
		 Vector3f vector = new Vector3f(convertToFloat(centerX+radius2+backgroundX), backgroundY , 0.0f);
		 Point3f atten = new Point3f(1.0f, 0.0f, 0.0f);
		 SpotLight light = new SpotLight(color,point, atten, vector,(float) Math.PI, 128.0f);
		 getInnerT().addChild(light);
		 return light;
	 }
}