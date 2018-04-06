/**
 *   file: myPanel.java
 */
package cis315GraphicsGame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JColorChooser;

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
	Point pntEndPrev = new Point();
	Cursor prevCursor; // for storage of previous cursor
	Color colorShapeFill = Color.WHITE;
	boolean isSolidFill = false;
	Color colorShapeOutline = Color.black;
	boolean isSolidOutline = true;

	private static final long serialVersionUID = 1L;
	ArrayList<Shape> myShapes = new ArrayList<>();

	@Override
	public void paint(Graphics g) {

		super.paint(g); // behave just like any panel

		// add additional painting

		// iterate through all Shapes in myDrawing
		for (Shape sh : myShapes) {
			// for starters, everything is an oval
			// will need to handle multiple shapes here
			// g.drawOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y -
			// sh.pntUL.y);

			// only draw inner oval if solid fill
			if (sh.getSolidFill() ) {
				// first draw the filled shape
				g.setColor(sh.colorFill);

				// add 1 to x,y due to outline width
				g.fillOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x + 1, sh.pntLR.y - sh.pntUL.y + 1);
			}

			// now the outline
			g.setColor(sh.colorOutline);
			g.drawOval(sh.pntUL.x, sh.pntUL.y, sh.pntLR.x - sh.pntUL.x, sh.pntLR.y - sh.pntUL.y);

			// todo: set text color and draw if the shape contains text

		}
	}

	void addShape(Shape sh) {
		myShapes.add(sh);
	}

	// set global outline color
	void setColorShapeOutline(Color c) {
		this.colorShapeOutline = c;
	}

	// set global fill color
	void setColorShapeFill(Color c) {
		this.colorShapeFill = c;
	}

	void setSolidFill(boolean isSolid) {
		this.isSolidFill = isSolid;
	}

	
	// show a color selection dialog for outline color selection
	void chooseColorOutline() {
		Color c = JColorChooser.showDialog(null, "Choose Outline Color", this.getBackground());
		this.colorShapeOutline = c;
	}

	// display a color selection dialog for fill color selection
	void chooseColorFill() {
		Color c = JColorChooser.showDialog(null, "Choose Outline Color", this.getBackground());
		this.colorShapeFill = c;
	}

	public void drawStart(String shapeName) {

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
		pntEndPrev.setLocation(pntStart);

	} // end dragStart

	public void mouseReleased(MouseEvent e) {
		// end the shape if being drawn
		if (isDrawing) {
			isDrawing = false; // done drawing

			// oval width and height cannot be negative - correct if needed
			int xStart = pntStart.x;
			int width = e.getX() - pntStart.x;

			if (width < 0) {
				xStart += width;
				width = -width;
			}

			int yStart = pntStart.y;
			int height = e.getY() - pntStart.y;
			if (height < 0) {
				yStart += height;
				height = -height;
			}

			Oval thisShape = new Oval(xStart, yStart, width, height);
			// set this shape's colors
			thisShape.setColors(this.colorShapeOutline, this.colorShapeFill);
			thisShape.setSolidFill(this.isSolidFill);
			this.myShapes.add(thisShape);

			// restore the panel Cursor
			this.setCursor(prevCursor);

			// force a repaint of this panel
			this.repaint();

			// the paint() method should draw everything in myDrawing
			// force a repaint here
		}

	} // end mouseReleased

	public void mouseMoved(MouseEvent e) {
		if (isDrawing) {
			// got some help from StackOverflow for this
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		}
	} // end mouseMoved

	public void mouseDragged(MouseEvent e) {
		// draw circle if mouse dragging
		// start point is mouse down
		if (isDrawing) {
			// only if a shape in progress

			// draw circle from startpoint to here
			Graphics g = this.getGraphics();

			// oval width and height cannot be negative - correct if needed
			int xStart = pntStart.x;
			int width = e.getX() - pntStart.x;

			if (width < 0) {
				xStart += width;
				width = -width;
			}

			int yStart = pntStart.y;
			int height = e.getY() - pntStart.y;
			if (height < 0) {
				yStart += height;
				height = -height;
			}

			// g.setXORMode(this.getBackground()); // trying to clean up drawing on drag

			if (this.isSolidFill) {
				g.setColor(this.colorShapeFill);
				g.fillOval(xStart, yStart, width + 1, height + 1); // add 1 for outline width
			}

			g.setXORMode(this.getBackground());
			g.setColor(this.colorShapeOutline);
			
			// erase previous oval first
			// oval width and height cannot be negative - correct if needed
			int xStartOld = pntStart.x;
			int widthOld = pntEndPrev.x - pntStart.x;

			if (widthOld < 0) {
				xStartOld += widthOld;
				widthOld = -widthOld;
			}

			int yStartOld = pntStart.y;
			int heightOld = pntEndPrev.y - pntStart.y;
			if (heightOld < 0) {
				yStartOld += heightOld;
				heightOld = -heightOld;
			}
			g.drawOval(xStartOld, yStartOld, widthOld, heightOld);
			
			
			
			
			g.drawOval(xStart, yStart, width, height);
			// the oval doesn't become permanent until
			// the mouse is released
			
			pntEndPrev.setLocation(e.getPoint());
		}

	}
}
