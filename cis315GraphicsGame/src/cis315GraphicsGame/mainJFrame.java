/**
 *   file: mainJFrame.java
 */
package cis315GraphicsGame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Label;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * @author atmanning - atmanning@dbq.edu
 *
 */
public class mainJFrame extends JFrame {

	private JPanel contentPane;

	// these items declared here for global access
	// from all ActionHandlers
	JTextArea textAreaHistory;
	Label labelDebug; // for displaying status
	myPanel panelDraw; // need access to this from paint()
	ArrayList<Shape> myDrawing = new ArrayList<>();

	// variables needed for drawing
	boolean isDrawing = false; // set true when drawing
	boolean isDrawingMouseDown = false; // when drawing object
	Point pntStart = new Point(); // starting point on click-drag
	Cursor prevCursor; // for storage of previous cursor

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainJFrame frame = new mainJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainJFrame() {
		setTitle("graphicsDrawing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		// drag menu items to the menu panel
		JMenuItem mntmQuit = new JMenuItem("Quit");

		// right-click on menu items to add event Listeners
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// end the program
				// todo: check for save
				System.exit(NORMAL);
			}
		});
		mnFile.add(mntmQuit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmSelectAll = new JMenuItem("Select All");
		mnEdit.add(mntmSelectAll);

		JMenu mnDraw = new JMenu("Draw");
		mnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuBar.add(mnDraw);

		JMenuItem mntmCircle = new JMenuItem("Circle");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// start drawing a Circle
				textAreaHistory.append("drawing Circle\n");
				labelDebug.setText("circle...");
				// change the cursor to indicate drawing
				isDrawing = true;

				// store cursor for later restore
				prevCursor = panelDraw.getCursor();

				// drawing begins with mouse click-drag
				// in the panel
				// drawing ends with mouse-release
				// add circle Object to drawing list
				// (panel paint() method draws this)
			}
		});
		mnDraw.add(mntmCircle);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelDraw = new myPanel();
		panelDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// store this location and start drawing
				pntStart.setLocation(e.getPoint());
				isDrawingMouseDown = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// end the shape if being drawn
				if (isDrawing) {
					isDrawing = false; // done drawing
					textAreaHistory.append("Oval Done\n");
					panelDraw.myShapes.add(new Oval(pntStart.x, pntStart.y, e.getX() - pntStart.x, e.getY() - pntStart.y));

					// restore the panel Cursor
					panelDraw.setCursor(prevCursor);
					
					// force a repaint here
					panelDraw.repaint();
					
					// the paint() method should draw everything in myDrawing
					// force a repaint here
				}

			}
		});

		panelDraw.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				if (isDrawing) {
					// got some help from StackOverflow for this
					panelDraw.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					labelDebug.setText("m:" + e.getX() + "," + e.getY());
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// draw circle if mouse dragging
				// start point is mouse down
				if (isDrawing) {
					// only if a shape in progress
					labelDebug.setText("d:" + e.getX() + "," + e.getY());

					// draw circle from startpoint to here
					Graphics g = panelDraw.getGraphics();

					g.drawOval(pntStart.x, pntStart.y, e.getX() - pntStart.x, e.getY() - pntStart.y);
					// the oval doesn't become permanent until
					// the mouse is released
				}

			}
		});
		panelDraw.setBackground(SystemColor.window);
		panelDraw.setBounds(12, 10, 301, 219);
		contentPane.add(panelDraw);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(330, 36, 96, 153);
		contentPane.add(scrollPane);

		textAreaHistory = new JTextArea();
		scrollPane.setViewportView(textAreaHistory);
		textAreaHistory.setBackground(new Color(240, 255, 255));

		Label label = new Label("History");
		label.setBounds(329, 10, 58, 20);
		contentPane.add(label);

		labelDebug = new Label("Debug...");
		labelDebug.setBounds(329, 209, 58, 20);
		contentPane.add(labelDebug);
	}

}
