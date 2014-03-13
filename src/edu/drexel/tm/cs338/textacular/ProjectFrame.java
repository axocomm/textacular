package edu.drexel.tm.cs338.textacular;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * The class ProjectFrame.
 * 
 * The main frame of the application that contains the input
 * panels and other program controls
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class ProjectFrame extends JFrame implements ActionListener {
	
	/**
	 * The width of the frame.
	 */
	protected static final int WIDTH = 1200;
	
	/**
	 * The height of the frame.
	 */
	protected static final int HEIGHT = 800;
	
	/**
	 * The menu bar.
	 */
	private JMenuBar menuBar;
	
	/**
	 * The main menu.
	 */
	private JMenu mainMenu;
	
	/**
	 * The Quit menu item.
	 */
	private JMenuItem itmQuit;
	
	/**
	 * The input panel tabs.
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * The template panel.
	 */
	private JPanel templatesPanel;
	
	/**
	 * The bottom buttons panel.
	 */
	private JPanel buttonsPanel;
	
	/**
	 * The preview panel.
	 */
	private JPanel previewPanel;
	
	/**
	 * The compile button.
	 */
	private JButton btnCompile;
	
	/**
	 * The clear button.
	 */
	private JButton btnClear;
	
	/**
	 * The options button.
	 */
	private JButton btnOptions;
	
	/**
	 * The view button.
	 */
	private JButton btnView;
	
	/**
	 * The template panels.
	 */
	private TemplatePanel[] panels = { 
			new LetterPanel(), new ArticlePanel(), new InvoicePanel() };
	
	/**
	 * Instantiate a new ProjectFrame.
	 */
	public ProjectFrame() {
		super("TeXtacular");
		
		createMenuBar();
		setJMenuBar(menuBar);

		tabbedPane = new JTabbedPane();
		addTabPanels();
		
		(btnCompile = new JButton("Compile")).addActionListener(this);
		(btnClear = new JButton("Clear")).addActionListener(this);
		(btnOptions = new JButton("Options")).addActionListener(this);
		(btnView = new JButton("View")).addActionListener(this);
		
		btnView.setEnabled(false);
		
		templatesPanel = new JPanel(new MigLayout());
		templatesPanel.add(tabbedPane);
		
		buttonsPanel = new JPanel(new MigLayout("wrap 4"));
		buttonsPanel.add(btnCompile);
		buttonsPanel.add(btnView);
		buttonsPanel.add(btnClear);
		buttonsPanel.add(btnOptions);
		
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		add(templatesPanel);
		add(buttonsPanel, BorderLayout.SOUTH);
		add(previewPanel, BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
	}
	
	/**
	 * Create the menu bar.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		mainMenu = new JMenu("File");
		menuBar.add(mainMenu);
		
		(itmQuit = new JMenuItem("Quit", KeyEvent.VK_Q)).addActionListener(this);
		mainMenu.add(itmQuit);
	}
	
	/**
	 * Add the template panel tabs.
	 */
	private void addTabPanels() {
		for (TemplatePanel panel : panels) {
			tabbedPane.add(panel.getTemplateName(), panel);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCompile) {
			TemplatePanel panel = (TemplatePanel) tabbedPane.getSelectedComponent();
			if (panel.checkInputs()) {
				panel.addVariables();
				if (panel.prepareHandler()) {
					if (panel.compile()) {
						System.out.println("Success");
						((PreviewPanel) previewPanel).refresh();
						btnView.setEnabled(true);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Could not prepare TeX file.",
							"Template Error", JOptionPane.ERROR_MESSAGE);
					btnView.setEnabled(false);
				}
			}
		} else if (e.getSource() == btnClear) {
			TemplatePanel panel = (TemplatePanel) tabbedPane.getSelectedComponent();
			panel.resetInputs();
		} else if (e.getSource() == btnOptions) {
			System.out.println("Options pressed");
		} else if (e.getSource() == btnView) {
			File outputFile = new File(((PreviewPanel) previewPanel).getOutputFilename());
			if (!outputFile.exists()) {
				JOptionPane.showMessageDialog(this, "Could not open PDF.",
						"View Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(outputFile);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Could not open PDF.",
							"View Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == itmQuit) {
			for (TemplatePanel panel : panels) {
				panel.cleanup();
			}
			
			dispose();
		}
	}
}
