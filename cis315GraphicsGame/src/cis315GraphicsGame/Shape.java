/**
 *   file: Shape.java
 */
package cis315GraphicsGame;

import java.awt.Color;
import java.awt.Point;
import java.awt.color.ColorSpace;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class Shape {
	// this is the parent class to all Shapes
	
	Point pntUL = new Point();  // defines bounding box
	Point pntLR = new Point();  // defines bounding box
	Color colorOutline = new Color(Color.black.getRGB());
	Color colorFill = new Color(Color.WHITE.getRGB());
	boolean isSolidFill = false;
	Color colorText = new Color(Color.GRAY.getRGB());
	boolean isSolidLine = true;
	
	
	// getters and setters 
	
	void setColors( Color cOutline, Color cFill ) {
		this.colorOutline = cOutline;
		this.colorFill = cFill;
	}
	
	void setSolidFill( boolean bSolid ) {
		isSolidFill = bSolid;
	}
	
	boolean getSolidFill() {
		return isSolidFill;
	}
	
}
