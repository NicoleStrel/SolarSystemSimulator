import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

class SolarOrbiter{
	 public static void main(String[] args)  { 
		 //For java 3d
		 System.setProperty("sun.awt.noerasebackground", "true");
		 JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		 ToolTipManager ttm = ToolTipManager.sharedInstance();
	     ttm.setLightWeightPopupEnabled(false);
		   
	     new MainMenu();
		  //new ExamplePanel(); 

     }
}




