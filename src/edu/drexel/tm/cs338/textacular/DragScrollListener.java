package edu.drexel.tm.cs338.textacular;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 * The class DragScrollListener.
 * 
 * Allows scrolling by dragging
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class DragScrollListener extends MouseAdapter {
	
	/**
	 * The default cursor.
	 */
	private Cursor dCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	/**
	 * The hand cursor.
	 */
	private Cursor hCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	/**
	 * The current point.
	 */
	private Point point;
	
	/**
	 * The panel being moved.
	 */
	private JPanel panel;
	
	/**
	 * Instantiate a new DragScrollListener.
	 * 
	 * @param panel the panel being moved
	 */
	public DragScrollListener(JPanel panel) {
		this.panel = panel;
		
		point = new Point();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		JViewport viewport = (JViewport) e.getSource();
		Point cp = e.getPoint();
		Point vp = viewport.getViewPosition();
		
		vp.translate(point.x - cp.x, point.y - cp.y);
		panel.scrollRectToVisible(new Rectangle(vp, viewport.getSize()));
		point.setLocation(cp);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		panel.setCursor(hCursor);
		point.setLocation(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		panel.setCursor(dCursor);
		panel.repaint();
	}
}
