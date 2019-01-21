import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.media.j3d.TransformGroup;

abstract class SpaceObject{
	protected double axialTilt; //degreees
	private double speedRotates; //pixels/s
	private String directionRotates;
	private String [] data;
	protected double radius;
	protected Color color;
	private String name;
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int centerX=(int)(screenSize.getWidth()/2);	     
	public static final int centerY=(int)(screenSize.getHeight()/2)-100;
	private TransformGroup transformAll;
	private TransformGroup innerTransform;
	
	
	SpaceObject(double radius){
		this.radius=radius;
	}
	SpaceObject(String name, double speedRotates, double axialTilt, String directionRotates, double radius, Color color, String [] data){
		this.name=name;
		this.speedRotates=speedRotates;
		this.axialTilt=axialTilt;
		this.directionRotates= directionRotates;
		this.radius=radius;
		this.color=color;
		this.data=data;
		resetTransforms();
	}
	public void resetTransforms() {
		transformAll = new TransformGroup();
		transformAll.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		innerTransform = new TransformGroup();
		transformAll.addChild(innerTransform);
	}
	public double getAngle() {
		return this.axialTilt;
	}
	public void setAngle(double axialTilt) {
		this.axialTilt=axialTilt;
	}
	public double getDaysRotates() {
		return this.speedRotates;
	}
	public void setDaysRotates(double daysRotates){
		this.speedRotates=daysRotates;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getDirection() {
		return this.directionRotates;
	}
	public void setDirection(String directionRotates) {
		this.directionRotates=directionRotates;
	}
	public double getRadius() {
		return this.radius;
	}
	public void setRadius(double radius) {
		this.radius=radius;
	}
	public Color getColor() {
		return this.color;
	}
	public void setColor(Color color) {
		this.color=color;
	}
	public int getCenterX() {
		return this.centerX;
	}
	public int getCenterY() {
		return this.centerY;
	}
	public String [] getData() {
		return this.data;
	}
	public TransformGroup getTransformGroup() {
		return this.transformAll;
	}
	public TransformGroup getInnerT() {
		return this.innerTransform;
	}
	 //pixels to millipoints
	 public float convertToFloat(double number) {
		 int resolution= Toolkit.getDefaultToolkit().getScreenResolution();
		 double conversion=(number*72)/(resolution*1000);
		 return (float) conversion;
	 }
}