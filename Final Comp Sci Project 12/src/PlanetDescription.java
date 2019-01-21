import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** 
   * [PlanetDescription.java]
   * @author Nicole Streltsov
   * A class (JPanel) that contains the planet description
   * January 2019
   */
  class PlanetDescription  extends JPanel {
	  private int startingX=40;
	  private int startingY=20;
	  private boolean off;
	  private String chosen;
	  private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  private ArrayList<SpaceObject> spaceObjects;
	  
	  PlanetDescription(boolean off, String chosen, ArrayList<SpaceObject> spaceObjects){
		  this.setBackground(Color.white);	 
	      this.setPreferredSize(new Dimension(300,(int)screenSize.getHeight()-400));
	      this.setMinimumSize(this.getPreferredSize());
	      this.setMaximumSize(this.getPreferredSize());
	      this.off=off;
	      this.chosen=chosen;
	      this.spaceObjects=spaceObjects;
	  }
	  public void setValues(boolean off, String chosen) {
		  this.off=off;
		  this.chosen=chosen;
		  repaint();
	  }
	  /** paintComponent *******************************************
	   * draws all the nessasary componets on the graphics panel(Solar System)
	   */
	  public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
	   	setDoubleBuffered(true); 
		if (off) {
			g.setColor(Color.black);
			int size= 12;
	        Font font= new Font("Gugi", Font.PLAIN, size);
	        g.setFont(font); 
			g.drawString("Click a planet to view it's information.", startingX, startingY);
		}
		else {
	        //find chosen planet
			 Iterator<SpaceObject> itr=spaceObjects.iterator();
			  while (itr.hasNext()) {
				   SpaceObject object = (SpaceObject)itr.next();
				   if (object.getName().equals(chosen)) {
					   g.setColor(Color.black);
					   g.drawLine(0, 0, 300, 0);
					   //name
					   int size= 40;
		               Font font= new Font("Gugi", Font.PLAIN, size);
		               g.setFont(font);
		               g.drawString(object.getName(), startingX, startingY+size);
			           BufferedImage picture = null;
					   try {picture = ImageIO.read(new File("images/planets/"+object.getName()+".png"));} catch (IOException e) {}
					   g.drawImage(picture,startingX+5+(object.getName().length()*25), startingY, null);
		               
		               //stats
		               int size2=12;
	            	   Font font2= new Font("Gugi", Font.PLAIN, size2);
		               g.setFont(font2);
		               int start=startingY+size;
	            	   int space=10;
		               if (object instanceof Sun) {
		            	   String[] sentances=(object.getData()[5]).split("@");
		            	   int r=1;
		            	   for (int i=0; i<sentances.length; i++) {
		            		   g.drawString(sentances[i], startingX, startingY+start+size2*r);
		            		   r++;
		            	   }
		            	   g.drawString("Radius: "+object.getData()[1]+" km", startingX,  startingY+start+(size2*(r+1))+space);
		            	   g.drawString("Rotational speed: "+object.getData()[2]+" km/h", startingX, startingY+start+(size2*(r+2))+space*2);
		            	   g.drawString("Axial tilt: "+object.getData()[3]+"° "+object.getData()[4], startingX, startingY+start+(size2*(r+3))+space*3); 
		               }
		               else {
		            	   String[] sentances=(object.getData()[9]).split("@");
		            	   int r=1;
		            	   for (int i=0; i<sentances.length; i++) {
		            		   g.drawString(sentances[i], startingX, startingY+start+size2*r);
		            		   r++;
		            	   }
		            	   g.drawString("Radius: "+object.getData()[1]+" km", startingX,  startingY+start+(size2*(r+1))+space);
		            	   g.drawString("Rotational speed: "+object.getData()[4]+" km/h", startingX, startingY+start+(size2*(r+2))+space*2);
		            	   g.drawString("Axial tilt: "+object.getData()[6]+"° "+object.getData()[7], startingX, startingY+start+(size2*(r+3))+space*3);
		            	   g.drawString("Distance from Sun: "+object.getData()[2]+" "+object.getData()[3]+" km", startingX, startingY+start+(size2*(r+4))+space*4);
		            	   g.drawString("Speed around Sun: "+object.getData()[5]+" km/s", startingX, startingY+start+(size2*(r+5))+space*5);
				               } 							   
						   }
		    	      }
		    	
		    	}
		    }
  }