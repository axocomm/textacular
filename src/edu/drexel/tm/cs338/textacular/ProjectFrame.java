package edu.drexel.tm.cs338.textacular;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
public class ProjectFrame extends JFrame {
	
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
	 * The bottom buttons panel.
	 */
	private JPanel buttonsPanel;
	
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
		
		btnCompile = new JButton("Compile");
		btnClear = new JButton("Clear");
		btnOptions = new JButton("Options");
		
		buttonsPanel = new JPanel(new MigLayout());
		buttonsPanel.add(btnCompile);
		buttonsPanel.add(btnClear);
		buttonsPanel.add(btnOptions, "gapleft 30");
		
		add(tabbedPane);
		add(buttonsPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
	}
	
	/**
	 * Create the menu bar.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		mainMenu = new JMenu("File");
		menuBar.add(mainMenu);
		
		itmQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
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
}
