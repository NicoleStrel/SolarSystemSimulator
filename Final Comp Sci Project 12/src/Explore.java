import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

class Explore extends JFrame{
	private static double x, y; 
    private static JFrame simulation;
    private static SolarSystem solarSystem;
    
	Explore(){
		super("Explore");  
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
	    this.setUndecorated(true);  
	    this.setResizable(false);
		    
	    //Set up frame 
	    solarSystem = new SolarSystem();
	    this.add(new SolarSystem());
	    
	    //-------control bar------
	    JPanel controlBar = new JPanel();
	    //controlBar.setBorder(new EmptyBorder(200, 20, 300, 50));
	    controlBar.setBackground(Color.red);
	   // controlBar.setBackground(new Color(255,255,255));
	    controlBar.setLayout(new BoxLayout(controlBar,  BoxLayout.Y_AXIS));

	    //control label
	    BufferedImage controls = null;
		try {controls = ImageIO.read(new File("images/controls.png"));} catch (IOException e) {}
	    JLabel controlLabel = new JLabel(new ImageIcon(controls));
	    controlBar.add(controlLabel);
	    
	    //scroll bars
	    controlBar.add(new JScrollBar(JScrollBar.HORIZONTAL));

	    
	    
	    this.add(controlBar);
	    //--------------------------
	    
	    //listeners
	    MyKeyListener keyListener = new MyKeyListener();
	    this.addKeyListener(keyListener);
	    MyMouseListener mouseListener = new MyMouseListener();
	    this.addMouseListener(mouseListener);

	    this.requestFocusInWindow(); 	    
	    this.setVisible(true);
	  
	    //Start the loop
	    Thread t = new Thread(new Runnable() { public void run() { run(); }}); 
	    t.start();
	}
	//loop
	  public void run() { 	    
	    while(true){
	      //update
	    	
	    	
	      try{ Thread.sleep(500);} catch (Exception exc){}  
	      this.repaint();
	    }    
	  }
	 /** --------- INNER CLASSES ------------- **/
	  //solar system panel
	  private class SolarSystem extends JPanel {
		SolarSystem() {
		      this.setBackground(Color.black);
		      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		      this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
		      this.setMinimumSize(this.getPreferredSize());
		      this.setMaximumSize(this.getPreferredSize());
		}
	    public void paintComponent(Graphics g) {   
	       super.paintComponent(g); 
	       setDoubleBuffered(true);  
	      
	    }
	  }
	  
	  //key listener
	   private class MyKeyListener implements KeyListener {
	  
	      public void keyTyped(KeyEvent e) {  
	      }

	      public void keyPressed(KeyEvent e) {
	      }   
	      
	      public void keyReleased(KeyEvent e) {
	      }
	    } 
	  
	   //mouse listener
	    private class MyMouseListener implements MouseListener {
	   
	      public void mouseClicked(MouseEvent e) {
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