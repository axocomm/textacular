package edu.drexel.tm.cs338.textacular;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import net.miginfocom.swing.MigLayout;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * The class PreviewPanel.
 * 
 * Views a (somewhat) zoomable PDF document that can be updated
 * at any time
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class PreviewPanel extends JPanel {
	
	/**
	 * The output filename.
	 */
	protected static final String OUTPUT_FILENAME = "res/output/output.pdf";
	
	/**
	 * The panel width.
	 */
	protected static final int WIDTH = 600;
	
	/**
	 * The panel height.
	 */
	protected static final int HEIGHT = 640;
	
	/**
	 * The image rendered from PDF.
	 */
	private BufferedImage image;
	
	/**
	 * The zoom in button.
	 */
	private JButton btnZoomIn;
	
	/**
	 * The zoom out button.
	 */
	private JButton btnZoomOut;
	
	/**
	 * The next page button.
	 */
	private JButton btnNext;
	
	/**
	 * The previous page button.
	 */
	private JButton btnPrevious;
	
	/**
	 * The image panel.
	 */
	private ImagePanel imagePanel;
	
	/**
	 * The buttons panel.
	 */
	private JPanel buttonsPanel;
	
	/**
	 * The page number.
	 */
	private int pageNo;
	
	/**
	 * The number of pages.
	 */
	private int nPages;
	
	/**
	 * Instantiate a new PreviewPanel.
	 */
	public PreviewPanel() {
		super(new MigLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		imagePanel = new ImagePanel(image, 10.0, WIDTH, HEIGHT);
		
		nPages = -1;
		
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
		
		btnNext = new JButton("->");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nPages > 0) {
					nextPage();
				}
			}
		});
		
		btnPrevious = new JButton("<-");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nPages > 0) {
					previousPage();
				}
			}
		});
		
		buttonsPanel = new JPanel();
		
		buttonsPanel.add(btnPrevious);
		buttonsPanel.add(btnZoomIn);
		buttonsPanel.add(btnZoomOut);
		buttonsPanel.add(btnNext);
		
		JScrollPane previewScrollPane = new JScrollPane(imagePanel);
		previewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		previewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		DragScrollListener dragListener = new DragScrollListener(imagePanel);
		previewScrollPane.getViewport().addMouseMotionListener(dragListener);
		previewScrollPane.getViewport().addMouseListener(dragListener);
		
		add(previewScrollPane);
		add(buttonsPanel, "dock south");
	}
	
	/**
	 * Refresh the image.
	 */
	public void refresh() {
		if (loadPdf()) {
			imagePanel.setImage(image);
			repaint();
			revalidate();
		}
	}
	
	/**
	 * Go to the next page.
	 */
	public void nextPage() {
		if (pageNo == nPages) {
			pageNo = 1;
		} else {
			pageNo++;
		}
		
		refresh();
	}
	
	/**
	 * Go to the previous page.
	 */
	public void previousPage() {
		if (pageNo == 1) {
			pageNo = nPages;
		} else {
			pageNo--;
		}
		
		refresh();
	}
	
	/**
	 * Load a PDF into a BufferedImage.
	 * 
	 * @return if the process was successful
	 */
	protected boolean loadPdf() {
		File f = new File(OUTPUT_FILENAME);
		if (f.exists() && !f.isDirectory()) {
			try {
				RandomAccessFile raf = new RandomAccessFile(f, "r");
				FileChannel fc = raf.getChannel();
				ByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				PDFFile pdfFile = new PDFFile(buf);
				
				nPages = pdfFile.getNumPages();
				System.out.println(nPages);
				PDFPage page = pdfFile.getPage(pageNo);
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
	
	/**
	 * Convert an Image to a BufferedImage.
	 * 
	 * @param img the Image
	 * @return a BufferedImage
	 */
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
	
	/**
	 * Get the output PDF filename.
	 * 
	 * @return the output PDF filename
	 */
	protected String getOutputFilename() {
		return OUTPUT_FILENAME;
	}
}
