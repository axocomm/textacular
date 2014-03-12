package edu.drexel.tm.cs338.textacular;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
	 * The template panels.
	 */
	private TemplatePanel[] panels = { 
			new LetterPanel(), new ArticlePanel() };
	
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
		
		templatesPanel = new JPanel(new MigLayout());
		templatesPanel.add(tabbedPane);
		
		buttonsPanel = new JPanel(new MigLayout());
		buttonsPanel.add(btnCompile);
		buttonsPanel.add(btnClear);
		buttonsPanel.add(btnOptions, "gapleft 30");
		
		previewPanel = new PreviewPanel();
		
		add(templatesPanel);
		add(buttonsPanel, BorderLayout.SOUTH);
		add(new JScrollPane(previewPanel), BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
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
					}
				} else {
					JOptionPane.showMessageDialog(this, "Could not prepare TeX file.",
							"Template Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == btnClear) {
			TemplatePanel panel = (TemplatePanel) tabbedPane.getSelectedComponent();
			panel.resetInputs();
		} else if (e.getSource() == btnOptions) {
			System.out.println("Options pressed");
		} else if (e.getSource() == itmQuit) {
			for (TemplatePanel panel : panels) {
				panel.cleanup();
			}
			
			dispose();
		}
	}
}
