//imports
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

/** 
* [SolarOrbiter.java]
* @author Nicole Streltsov
* The class that initiates the program with its main method
* January 2019
*/
class SolarOrbiter{
	 /** Main method *******************************************
     * @param arguements
     */ 
	 public static void main(String[] args)  { 
		 //Things needed for java 3d
		 System.setProperty("sun.awt.noerasebackground", "true");
		 JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		 ToolTipManager ttm = ToolTipManager.sharedInstance();
	     ttm.setLightWeightPopupEnabled(false);
		   
	     //generate the staring screen
	     new MainMenu();
     }
}




