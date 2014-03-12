package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import net.miginfocom.swing.MigLayout;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

class ImagePanel extends JPanel {
	private double zoom;
	private double percentage;
	
	private int width;
	private int height;
	
	private BufferedImage image;
	
	private DragListener dragListener;
	
	public ImagePanel(BufferedImage image, double zoomPercentage, int width, int height) {
		this.image = image;
		percentage = zoomPercentage / 100.0;
		zoom = 1.0;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.WHITE);
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

public class PreviewPanel extends JPanel {
	protected static final int WIDTH = 600;
	protected static final int HEIGHT = 640;
	
	private BufferedImage image;
	
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	
	private ImagePanel imagePanel;
	
	private JPanel buttonsPanel;
	
	public PreviewPanel() {
		super(new MigLayout());
		// setBackground(Color.WHITE);
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
		
		JScrollPane previewScrollPane = new JScrollPane(imagePanel);
		DragListener dragListener = new DragListener(imagePanel);
		previewScrollPane.getViewport().addMouseMotionListener(dragListener);
		previewScrollPane.getViewport().addMouseListener(dragListener);
		
		add(previewScrollPane);
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
				
				image = getBufferedImage(page.getImage((int) w, (int) h, r2d, null, true, true));
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.printf("%s does not exist.", f.getAbsolutePath());
		}
		
		return false;
	}
	
	protected BufferedImage getBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		
		return bi;
		
	}
}
