package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

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

public class PreviewPanel extends JPanel {
	protected static final int WIDTH = 600;
	protected static final int HEIGHT = 640;
	
	private Image image;
	
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	
	private ImagePanel imagePanel;
	
	private JPanel buttonsPanel;
	
	public PreviewPanel() {
		super(new MigLayout());
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		imagePanel = new ImagePanel(image, 10.0, WIDTH, HEIGHT);
		
		btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePanel.zoomIn();
			}
		});
		
		btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePanel.zoomOut();
			}
		});
		
		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		
		buttonsPanel.add(btnZoomIn);
		buttonsPanel.add(btnZoomOut);
		
		add(imagePanel);
		add(buttonsPanel, "dock south");
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
