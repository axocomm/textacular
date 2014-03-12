package edu.drexel.tm.cs338.textacular;

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
	 * The text components.
	 */
	private JTextComponent[] textComponents;
	
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
		super("Letter", new MigLayout("wrap 4"));
		
		lblSignature = new JLabel("Signature");
		lblAddress = new JLabel("Address");
		lblTo = new JLabel("To");
		lblOpening = new JLabel("Opening");
		lblContent = new JLabel("Content");
		lblClosing = new JLabel("Closing");
		
		textComponents = new JTextComponent[] {
				txtSignature = new JTextField(20),
				txtOpening = new JTextField(20),
				txtClosing = new JTextField(20),
				txtAddress = new JTextArea(5, 20),
				txtTo = new JTextArea(5, 20),
				txtContent = new JTextArea(10, 40)
		};
		
		add(lblAddress);
		add(new JScrollPane(txtAddress));
		add(lblTo);
		add(new JScrollPane(txtTo), "wrap");
		add(lblOpening);
		add(txtOpening, "wrap");
		add(lblContent);
		add(new JScrollPane(txtContent), "span");
		add(lblClosing);
		add(txtClosing, "wrap");
		add(lblSignature);
		add(txtSignature, "wrap");
	}

	@Override
	public boolean checkInputs() {
		for (JTextComponent textComponent : textComponents) {
			if (textComponent.getText().length() <= 0) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void resetInputs() {
		for (JTextComponent textComponent : textComponents) {
			textComponent.setText("");
		}
	}
}
