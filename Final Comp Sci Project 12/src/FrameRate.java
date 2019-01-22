//import
import java.awt.Graphics;

/** 
* [FrameRate.java]
*  @author Nicole Streltsov
*  Measures the frame rate of the system real time
* January 2019
*/
class FrameRate { 
	  String frameRate; 
	  long lastTimeCheck; 
	  long deltaTime; 
	  int frameCount;
      
	  /** FrameRate *******************************************
		* constructor for the frame rate class
		*/
	  public FrameRate() { 
	    lastTimeCheck = System.currentTimeMillis();
	    frameCount=0;
	    frameRate="0 fps";
	  }
	  /** update *******************************************
	    * updates the current frame rate at the given time
	    */
	  public void update() { 
	  long currentTime = System.currentTimeMillis(); 
	    deltaTime += currentTime - lastTimeCheck;
	    lastTimeCheck = currentTime; 
	    frameCount++; 
	    if (deltaTime>=1000) { 
	      frameRate = frameCount + " fps" ;
	      frameCount=0; 
	      deltaTime=0;     
	    }
	  }
	  /** draw *******************************************
	    * draws the current frame rate out onto the screen
	    */
	   public void draw(Graphics g, int x, int y) {
	      g.drawString(frameRate,x,y); //display the frameRate
	   }
}