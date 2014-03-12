package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

class ImagePanel extends JPanel {
	private double zoom;
	private double percentage;
	
	private int width;
	private int height;
	
	private Image image;
	
	public ImagePanel(Image image, double zoomPercentage, int width, int height) {
		this.image = image;
		percentage = zoomPercentage / 100.0;
		zoom = 1.0;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
	}
	
	public void setImage(Image image) {
		this.image = image;
		repaint();
		revalidate();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(zoom, zoom);
		g2d.drawImage(image, 0, 0, this);
	}
	
	public void setZoomPercentage(int zoomPercentage) {
		percentage = ((double) zoomPercentage) / 100.0;
	}
	
	public void reset() {
		zoom = 1.0;
	}
	
	public void zoomIn() {
		zoom += percentage;
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
	}
}

public class PreviewPanel extends JPanel {
	private Image image;
	
	private ImageIcon icon;
	
	private JLabel label;
	
	private ImagePanel imagePanel;
	
	public PreviewPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(500, 768));
		imagePanel = new ImagePanel(image, 10.0, 500, 768);
		
		add(imagePanel);
	}
	
	public void refresh() {
		if (loadPdf()) {
			imagePanel.setImage(image);
			repaint();
			revalidate();
		}
	}
	
	protected boolean loadPdf() {
		String filename = "res/output/output.pdf";
		File f = new File(filename);
		if (f.exists() && !f.isDirectory()) {
			try {
				RandomAccessFile raf = new RandomAccessFile(f, "r");
				FileChannel fc = raf.getChannel();
				ByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				PDFFile pdfFile = new PDFFile(buf);
				
				int nPages = pdfFile.getNumPages();
				System.out.println(nPages);
				PDFPage page = pdfFile.getPage(1);
				Rectangle2D r2d = page.getBBox();
				
				double w = r2d.getWidth();
				double h = r2d.getHeight();
				
				image = page.getImage((int) w, (int) h, r2d, null, true, true);
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.printf("%s does not exist.", f.getAbsolutePath());
		}
		
		return false;
	}
}
