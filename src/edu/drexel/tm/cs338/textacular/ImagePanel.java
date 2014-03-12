package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * The class ImagePanel.
 * 
 * Handles displaying and zooming a BufferedImage
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class ImagePanel extends JPanel {
	
	/**
	 * The zoom amount.
	 */
	private double zoom;
	
	/**
	 * The percentage by which the zoom will be increased or decreased.
	 */
	private double percentage;
	
	/**
	 * The default width of the panel.
	 */
	private int width;
	
	/**
	 * The default height of the panel.
	 */
	private int height;
	
	/**
	 * The image to display.
	 */
	private BufferedImage image;
	
	/**
	 * Instantiate a new ImagePanel.
	 * 
	 * @param image the BufferedImage
	 * @param zoomPercentage the zoom amount
	 * @param width the starting width
	 * @param height the starting height
	 */
	public ImagePanel(BufferedImage image, double zoomPercentage, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
		
		percentage = zoomPercentage / 100.0;
		zoom = 1.0;
		setBackground(Color.WHITE);
	}
	
	@Override
	public Dimension getPreferredSize() {
		int w, h;
		if (image != null) {
			w = image.getWidth();
			h = image.getHeight();
		} else {
			w = width;
			h = height;
		}
		
		return new Dimension((int) zoom * w, (int) zoom * h);
	}
	
	/**
	 * Set the image to be displayed.
	 * 
	 * @param image the image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
		revalidate();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			int w = getWidth();
			int h = getHeight();
			int iw = image.getWidth();
			int ih = image.getHeight();
			
			double x = (w - zoom * iw) / 2;
			double y = (h - zoom * ih) / 2;
			
			AffineTransform at = AffineTransform.getTranslateInstance(x, y);
			at.scale(zoom, zoom);
			g2d.drawRenderedImage(image, at);
		}
	}
	
	/**
	 * Reset the zoom.
	 */
	public void reset() {
		zoom = 1.0;
		repaint();
	}
	
	/**
	 * Zoom in.
	 */
	public void zoomIn() {
		zoom += percentage;
		repaint();
	}
	
	/**
	 * Zoom out.
	 * 
	 * Reset the zoom if it becomes too small
	 */
	public void zoomOut() {
		zoom -= percentage;
		if (zoom < percentage) {
			if (percentage > 1.0) {
				zoom = 1.0;
			} else {
				zoomIn();
			}
		}
		
		repaint();
	}
}