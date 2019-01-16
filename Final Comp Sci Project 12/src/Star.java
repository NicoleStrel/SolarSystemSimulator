import java.awt.Color;
import java.awt.Graphics;

class Star{
	private int x;
	private int y;
	
	Star (int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void draw(Graphics g) {
		 g.setColor(Color.white);
		 g.fillOval(x, y, 2,2);
	}
}