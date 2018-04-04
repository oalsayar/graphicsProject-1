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
 * (replace this header with your information)
 * 
 * This is the starting point for a Java-based
 * CAD program.  Although not a pure Model-View-Controller
 * (MVC) program, you are encouraged to separate
 * the drawing code from the file-saving/reading code.
 * Try to keep the drawing methods isolated to myPanel.
 * 
 *  The initial graphic layout was done with the
 *  Eclipse designer to make it easier to adjust the
 *  look of the main page.
 *
 */
public class mainJFrame extends JFrame {

	private JPanel contentPane;

	// these items declared here for global access
	// from all ActionHandlers
	JTextArea textAreaHistory;
	Label labelDebug; // for displaying status
	drawingPanel panelDraw; // need access to this from paint()
	ArrayList<Shape> myDrawing = new ArrayList<>();


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

		JMenuItem mntmCircle = new JMenuItem("Oval");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// start drawing an Oval
				textAreaHistory.append("drawing Oval\n");
				labelDebug.setText("oval...");

				panelDraw.drawStart("Oval");
			}
		});
		mnDraw.add(mntmCircle);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelDraw = new drawingPanel();
		panelDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// store this location and start drawing

				panelDraw.dragStart(e);

			}

			@Override
			public void mouseReleased(MouseEvent e) {

				panelDraw.mouseReleased( e );
			}
		});

		panelDraw.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
	
				labelDebug.setText("m:" + e.getX() + "," + e.getY());

				panelDraw.mouseMoved( e );

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				labelDebug.setText("d:" + e.getX() + "," + e.getY());

				panelDraw.mouseDragged(e );

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
