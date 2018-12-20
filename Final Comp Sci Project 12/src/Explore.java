import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Explore extends JFrame{
	private static double x, y; 
    private static JFrame simulation;
    private static SolarSystem solarSystem;
    
	Explore(){
		super("My Game");  
	    // Set the frame to full screen 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	   // this.setUndecorated(true);  //Set to true to remove title bar
	   //frame.setResizable(false);


	    
	    //Set up the game panel (where we put our graphics)
	    solarSystem = new SolarSystem();
	    this.add(new SolarSystem());
	    
	    MyKeyListener keyListener = new MyKeyListener();
	    this.addKeyListener(keyListener);

	    MyMouseListener mouseListener = new MyMouseListener();
	    this.addMouseListener(mouseListener);

	    this.requestFocusInWindow(); //make sure the frame has focus   
	    
	    this.setVisible(true);
	  
	    //Start the game loop in a separate thread
	    Thread t = new Thread(new Runnable() { public void run() { run(); }}); //start the gameLoop 
	    t.start();
	}
	//the main gameloop - this is where the game state is updated
	  public void run() { 
	    
	    while(true){
	      this.x = (Math.random()*1024);  //update coords
	      this.y = (Math.random()*768);
	      try{ Thread.sleep(500);} catch (Exception exc){}  //delay
	      this.repaint();
	    }    
	  }
	 /** --------- INNER CLASSES ------------- **/
	  // Inner class for the the game area - This is where all the drawing of the screen occurs
	  private class SolarSystem extends JPanel {
	    public void paintComponent(Graphics g) {   
	       super.paintComponent(g); //required
	       setDoubleBuffered(true); 
	       g.setColor(Color.BLUE); //There are many graphics commands that Java can use
	       g.fillRect((int)x, (int)y, 50, 50); //notice the x,y variables that we control from our animate method      
	      
	    }
	  }
	  
	  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
	    private class MyKeyListener implements KeyListener {
	  
	      public void keyTyped(KeyEvent e) {  
	      }

	      public void keyPressed(KeyEvent e) {
	        //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
	       
	        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
	          System.out.println("YIKES D KEY!");
	        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
	          System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
	        } 
	      }   
	      
	      public void keyReleased(KeyEvent e) {
	      }
	    } //end of keyboard listener
	  
	  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
	    private class MyMouseListener implements MouseListener {
	   
	      public void mouseClicked(MouseEvent e) {
	        System.out.println("Mouse Clicked");
	        System.out.println("X:"+e.getX() + " y:"+e.getY());
	      }

	      public void mousePressed(MouseEvent e) {
	      }

	      public void mouseReleased(MouseEvent e) {
	      }

	      public void mouseEntered(MouseEvent e) {
	      }

	      public void mouseExited(MouseEvent e) {
	      }
	    } //end of mouselistener
}