import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

/*
class ExamplePanel extends JFrame{
	JFrame bleh;
	ExamplePanel(){
		super("Testing 123");
		
		this.bleh=this;				
		this.getContentPane().add(new ThreeDPanel());
		this.requestFocusInWindow();        
	    this.pack();
	    this.setVisible(true);
	   
	}

}
*/
/*
class ThreeDPanel extends JPanel{
	private JPanel threeD;
	private Clock clock;
	private FrameRate frameRate;
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public ThreeDPanel() {
		this.setBackground(Color.black);	 
	    this.setPreferredSize(new Dimension(((int)screenSize.getWidth())-300,(int)screenSize.getHeight()));
	    this.setMinimumSize(this.getPreferredSize());
	    this.setMaximumSize(this.getPreferredSize());
	    this.setLayout(new BorderLayout());
	    clock=new Clock();
	    frameRate=new FrameRate();
	    this.setUp();
	}
	public void setUp(){
         GraphicsConfiguration config= SimpleUniverse.getPreferredConfiguration();
         Canvas3D canvas= new Canvas3D(config);
		 SimpleUniverse universe = new SimpleUniverse(canvas);
		 BranchGroup group = new BranchGroup();
		 group.addChild(drawSphere());
		 this.add ("Center", canvas);
		 universe.getViewingPlatform().setNominalViewingTransform();
		 universe.getViewer().getView().setBackClipDistance(100.0);
		 universe.addBranchGraph(group);
		 //canvas.setFocusable(true);
		 //canvas.requestFocus();
		
	}
	 public Sphere drawSphere() { 
		 //add sphere
		 Appearance app = new Appearance();		 
		 ColoringAttributes color= new ColoringAttributes(new Color3f(1.0f, 0.0f, 0.0f), 1);
		 app.setColoringAttributes(color);
		 Sphere sphere= new Sphere(0.3f, Sphere.GENERATE_NORMALS, 120,app);
		
		 return sphere;
	 }
}
*/