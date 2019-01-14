import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

class Sphere {
	private double radius;
	private ArrayList<Point> points =new ArrayList <Point>();
	private ArrayList<Triangle> faces = new ArrayList<Triangle>();
	
	Sphere(double radius){
		this.radius=radius;
	}
	
	public void createIcosahedron(){
		// create 12 vertices of a icosahedron
		int t = (int)((1.0 + Math.sqrt(5.0)) / 2);
		points.add(new Point (-1, t, 0));
		points.add(new Point (1, t, 0));
		points.add(new Point (-1, -t, 0));
		points.add(new Point (1, -t, 0));
		
		points.add(new Point (0, -1, t));
		points.add(new Point (0, 1, t));
		points.add(new Point (0, -1,-t));
		points.add(new Point (0, 1,-t));
		
		points.add(new Point (t, 0,-1));
		points.add(new Point (t, 0,1));
		points.add(new Point (-t, 0,-1));
		points.add(new Point (-t, 0,1));
		
		
		// create 20 triangles of the icosahedron
		// 5 faces around point 0
		faces.add(new Triangle(0, 11, 5));
		faces.add(new Triangle(0, 5, 1));
		faces.add(new Triangle(0, 1, 7));
		faces.add(new Triangle(0, 7, 10));
		faces.add(new Triangle(0, 10, 11));

		// 5 adjacent faces
		faces.add(new Triangle(1, 5, 9));
		faces.add(new Triangle(5, 11, 4));
		faces.add(new Triangle(11, 10, 2));
		faces.add(new Triangle(10, 7, 6));
		faces.add(new Triangle(7, 1, 8));

		// 5 faces around point 3
		faces.add(new Triangle(3, 9, 4));
		faces.add(new Triangle(3, 4, 2));
		faces.add(new Triangle(3, 2, 6));
		faces.add(new Triangle(3, 6, 8));
		faces.add(new Triangle(3, 8, 9));

		// 5 adjacent faces
		faces.add(new Triangle(4, 9, 5));
		faces.add(new Triangle(2, 4, 11));
		faces.add(new Triangle(6, 2, 10));
		faces.add(new Triangle(8, 6, 7));
		faces.add(new Triangle(9, 8, 1));
		refineIcosahedron(3);
	}
	private void refineIcosahedron(int recursionLevel) {  //get each triangle to have 4 triangles
		//base case
		if (recursionLevel==0) {
			//do nothing
		}
		else {
		 // refine triangles
		  ArrayList<Triangle> faces2 = new ArrayList<Triangle>();
		  Iterator<Triangle> itr=faces.iterator();
		  while (itr.hasNext()) {
			  Triangle triangle = itr.next();   
		      // replace triangle by 4 triangles
		      int a = getMiddlePoint(triangle.getV1(), triangle.getV2());
		      int b = getMiddlePoint(triangle.getV2(), triangle.getV3());
		      int c = getMiddlePoint(triangle.getV3(), triangle.getV1());
	
		      faces2.add(new Triangle(triangle.getV1(), a, c));
		      faces2.add(new Triangle(triangle.getV2(), b, a));
		      faces2.add(new Triangle(triangle.getV3(), c, b));
		      faces2.add(new Triangle(a, b, c));
		  }
		  faces = faces2;
		  refineIcosahedron(recursionLevel-1);
		}
		
	}
	private int getMiddlePoint(int p1, int p2) {
		return 1;
	}
	public void drawSphere(Graphics g, Color color) {
		//iterate through triangles and draw them
		  Iterator<Triangle> itr=faces.iterator();
		  while (itr.hasNext()) {
			  Triangle triangle = itr.next();   
			  triangle.draw(g, color);
		  }
		 
	}
	
}