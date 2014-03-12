package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private double zoom;
	private double percentage;
	
	private int width;
	private int height;
	
	private BufferedImage image;
	
	public ImagePanel(BufferedImage image, double zoomPercentage, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
		
		percentage = zoomPercentage / 100.0;
		zoom = 1.0;
		setBackground(Color.WHITE);
	}
	
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
	
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
		revalidate();
	}
	
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
	
	public void setZoomPercentage(int zoomPercentage) {
		percentage = ((double) zoomPercentage) / 100.0;
	}
	
	public void reset() {
		zoom = 1.0;
	}
	
	public void zoomIn() {
		zoom += percentage;
		repaint();
	}
	
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