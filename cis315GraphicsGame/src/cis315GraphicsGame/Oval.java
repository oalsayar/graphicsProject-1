/**
 *   file: Oval.java
 */
package cis315GraphicsGame;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class Oval extends Shape {
	boolean isCircle;  // is this a circle?
	
	Oval(int x, int y, int width, int height) { // Constructor
		this.pntUL.setLocation(x, y);
		this.pntLR.setLocation(x+width, y+height);
	} // end constructor
}
