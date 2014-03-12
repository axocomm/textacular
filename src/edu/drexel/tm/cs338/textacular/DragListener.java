package edu.drexel.tm.cs338.textacular;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JViewport;


public class DragListener extends MouseAdapter {
	private Cursor dCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	private Cursor hCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	private Point point;
	
	private JPanel panel;
	
	public DragListener(JPanel panel) {
		this.panel = panel;
		
		point = new Point();
	}
	
	public void mouseDragged(MouseEvent e) {
		JViewport viewport = (JViewport) e.getSource();
		Point cp = e.getPoint();
		Point vp = viewport.getViewPosition();
		
		vp.translate(point.x - cp.x, point.y - cp.y);
		panel.scrollRectToVisible(new Rectangle(vp, viewport.getSize()));
		point.setLocation(cp);
	}
	
	public void mousePressed(MouseEvent e) {
		panel.setCursor(hCursor);
		point.setLocation(e.getPoint());
	}
	
	public void mouseReleased(MouseEvent e) {
		panel.setCursor(dCursor);
		panel.repaint();
	}
}
