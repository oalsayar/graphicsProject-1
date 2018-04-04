/**
 *   file: myPanel.java
 */
package cis315GraphicsGame;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class drawingPanel extends Panel {

	/**
	 * 
	 */
	
	// variables needed for drawing
	boolean isDrawing = false; // set true when drawing
	boolean isDrawingMouseDown = false; // when drawing object
	Point pntStart = new Point(); // starting point on click-drag
	Cursor prevCursor; // for storage of previous cursor

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
	
	
	public void drawStart( String shapeName ) {
		
		// change the cursor to indicate drawing
		isDrawing = true;

		// store cursor for later restore
		prevCursor = this.getCursor();

		// drawing begins with mouse click-drag
		// in the panel
		// drawing ends with mouse-release
		// add circle Object to drawing list
		// (panel paint() method draws this)

	}
	
	public void dragStart(MouseEvent e) {
		
		pntStart.setLocation(e.getPoint());
		isDrawingMouseDown = true;
		
	}  // end dragStart
	
	public void mouseReleased( MouseEvent e ) {
		// end the shape if being drawn
		if (isDrawing) {
			isDrawing = false; // done drawing
			
			this.myShapes.add(new Oval(pntStart.x, pntStart.y, e.getX() - pntStart.x, e.getY() - pntStart.y));

			// restore the panel Cursor
			this.setCursor(prevCursor);
			
			// force a repaint here
			this.repaint();
			
			// the paint() method should draw everything in myDrawing
			// force a repaint here
		}

	} // end mouseReleased
	
	
	public void mouseMoved(MouseEvent e ) {
		if (isDrawing) {
			// got some help from StackOverflow for this
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			
		}
	} // end mouseMoved
	
	public void mouseDragged( MouseEvent e ) {
		// draw circle if mouse dragging
		// start point is mouse down
		if (isDrawing) {
			// only if a shape in progress

			// draw circle from startpoint to here
			Graphics g = this.getGraphics();

			g.drawOval(pntStart.x, pntStart.y, e.getX() - pntStart.x, e.getY() - pntStart.y);
			// the oval doesn't become permanent until
			// the mouse is released
		}

	}
}
