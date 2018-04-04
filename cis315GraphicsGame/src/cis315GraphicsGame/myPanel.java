/**
 *   file: myPanel.java
 */
package cis315GraphicsGame;

import java.awt.Graphics;
import java.awt.Panel;
import java.util.ArrayList;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class myPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Shape> myShapes = new ArrayList<>();
	
	@Override
	public
	void paint(Graphics g) {
		
		super.paint(g); // behave just like any panel
		
		// add additional painting
		
		// iterate through all Shapes in myDrawing
		for (Shape sh : myShapes) {
			// for starters, everything is an oval
			// will need to handle multiple shapes here
			g.drawOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y - sh.pntUL.y);

		}
	}
	
	void addShape(Shape sh) {
		myShapes.add(sh);
	}
}
