//imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class MainMenu extends JFrame{
	private JFrame menu;
	MainMenu(){
		super("Start Screen");
	    this.menu = this; 
	    
	    //configure the window
	    this.setSize(400,700);    
	    this.setLocationRelativeTo(null); //start the frame in the center of the screen
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    this.setResizable (false);
	    
	    this.setUndecorated(true);
	    setBackground(new Color(0,0,0,0));
	    
	    //Create a MainMenuPanel
	    JPanel mainMenuPanel = new JPanel();
	    mainMenuPanel.setLayout(new BorderLayout());
	    mainMenuPanel.setBorder(new EmptyBorder(768-240*2, 68, 68, 68));
	    
	    JButton startButton = new JButton("Explore");
	    startButton.addActionListener(new StartButtonListener());
	    	    
	    mainMenuPanel.add(startButton,BorderLayout.SOUTH);
	    this.add(mainMenuPanel);
	    
	    //Start
	    this.setVisible(true);
	}
	 //This is an inner class that is used to detect a button press
	 class StartButtonListener implements ActionListener {  //this is the required class definition
	    public void actionPerformed(ActionEvent event)  {  
	      System.out.println("Starting new Game");
	      menu.dispose();
	      new Explore(); //create a new FunkyFrame (another file that extends JFrame)

	    }

	  }
}