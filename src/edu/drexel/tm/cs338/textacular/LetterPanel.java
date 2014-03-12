package edu.drexel.tm.cs338.textacular;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

/**
 * The class LetterPanel.
 * 
 * Displays inputs pertaining to letter creation
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class LetterPanel extends TemplatePanel {
	
	/**
	 * The template name.
	 */
	private static final String TEMPLATE_NAME = "Letter";
	
	/**
	 * The template filename.
	 */
	private static final String TEMPLATE_FILENAME = "letter.tex";
	
	/**
	 * The signature label.
	 */
	private JLabel lblSignature;
	
	/**
	 * The address label.
	 */
	private JLabel lblAddress;
	
	/**
	 * The addressee label.
	 */
	private JLabel lblTo;
	
	/**
	 * The opening label.
	 */
	private JLabel lblOpening;
	
	/**
	 * The content label.
	 */
	private JLabel lblContent;
	
	/**
	 * The closing label.
	 */
	private JLabel lblClosing;
	
	/**
	 * The date label.
	 */
	private JLabel lblDate;
	
	/**
	 * The signature text field.
	 */
	private JTextField txtSignature;
	
	/**
	 * The opening text field.
	 */
	private JTextField txtOpening;
	
	/**
	 * The closing text field.
	 */
	private JTextField txtClosing;
	
	/**
	 * The date text field.
	 */
	private JTextField txtDate;
	
	/**
	 * The writer address text area.
	 */
	private JTextArea txtAddress;
	
	/**
	 * The addressee text area.
	 */
	private JTextArea txtTo;
	
	/**
	 * The content text area.
	 */
	private JTextArea txtContent;
	
	/**
	 * Instantiate a new LetterPanel.
	 */
	public LetterPanel() {
		super(TEMPLATE_NAME, TEMPLATE_FILENAME, new MigLayout("wrap 4"));
		
		lblSignature = new JLabel("Signature");
		lblAddress = new JLabel("Address");
		lblTo = new JLabel("To");
		lblDate = new JLabel("Date");
		lblOpening = new JLabel("Opening");
		lblContent = new JLabel("Content");
		lblClosing = new JLabel("Closing");
		
		inputs.put("signature", txtSignature = new JTextField(20));
		inputs.put("opening", txtOpening = new JTextField(20));
		inputs.put("closing", txtClosing = new JTextField(20));
		inputs.put("address", txtAddress = new JTextArea(5, 20));
		inputs.put("date", txtDate = new JTextField(20));
		inputs.put("to", txtTo = new JTextArea(5, 20));
		inputs.put("content", txtContent = new JTextArea(10, 40));
		
		add(lblAddress);
		add(new JScrollPane(txtAddress));
		add(lblTo);
		add(new JScrollPane(txtTo), "wrap");
		add(lblDate);
		add(txtDate, "wrap");
		add(lblOpening);
		add(txtOpening, "wrap");
		add(lblContent);
		add(new JScrollPane(txtContent), "span");
		add(lblClosing);
		add(txtClosing, "wrap");
		add(lblSignature);
		add(txtSignature, "wrap");
	}
}
