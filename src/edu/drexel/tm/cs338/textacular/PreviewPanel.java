package edu.drexel.tm.cs338.textacular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PreviewPanel extends JPanel {
	private Image image;
	
	private ImageIcon icon;
	
	private JLabel label;
	
	public PreviewPanel() {
		setBackground(Color.RED);
		setPreferredSize(new Dimension(300, 600));
		
		icon = new ImageIcon();
		
		label = new JLabel();
		label.setVerticalAlignment(JLabel.TOP);
		add(new JScrollPane(label));
	}
	
	public void refresh() {
		if (loadPdf()) {
			icon.setImage(image);
			label.setIcon(icon);
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
				w /= 72.0;
				h /= 72.0;
				int res = Toolkit.getDefaultToolkit().getScreenResolution();
				w *= res;
				h *= res;
				
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
