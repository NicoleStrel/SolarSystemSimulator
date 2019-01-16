import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

abstract class SpaceObject{
	private double axialTilt; //degreees
	private double speedRotates; //pixels/s
	private String directionRotates;
	private String [] data;
	protected double radius;
	protected Color color;
	private String name;
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int centerX=(int)(screenSize.getWidth()/2);	     
	public static final int centerY=(int)(screenSize.getHeight()/2)-100;
	
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
}