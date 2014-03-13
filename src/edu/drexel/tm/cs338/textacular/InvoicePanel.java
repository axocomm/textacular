package edu.drexel.tm.cs338.textacular;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;

/**
 * The class HourRow.
 * 
 * Represents a time entry
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
class HourRow {
	
	/**
	 * The note.
	 */
	private String note;
	
	/**
	 * The hours worked.
	 */
	private double hours;
	
	/**
	 * Instantiate a new HourRow.
	 * 
	 * @param note the note
	 * @param hours the hours worked
	 */
	public HourRow(String note, double hours) {
		this.note = note;
		this.hours = hours;
	}
	
	/**
	 * Get the note.
	 * 
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Get the hours worked.
	 * 
	 * @return the hours worked
	 */
	public double getHours() {
		return hours;
	}
	
	/**
	 * Set the note.
	 * 
	 * @param note the new note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Set the hours worked.
	 * 
	 * @param hours the hours worked
	 */
	public void setHours(double hours) {
		this.hours = hours;
	}
}

/**
 * The class InvoiceRowTableModel.
 * 
 * Represents a table of HourRows
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
class InvoiceRowTableModel extends AbstractTableModel {
	
	/**
	 * The note column.
	 */
	private static final int NOTE_INDEX = 0;
	
	/**
	 * The hours column.
	 */
	private static final int HOURS_INDEX = 1;

	/**
	 * The column names.
	 */
	private String[] columnNames = { "Note", "Hours" };
	
	/**
	 * The rows.
	 */
	private ArrayList<HourRow> data;
	
	/**
	 * Instantiate a new InvoiceRowTableModel.
	 */
	public InvoiceRowTableModel() {
		data = new ArrayList<HourRow>();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		HourRow hourRow = (HourRow) data.get(row);
		
		switch (col) {
		case NOTE_INDEX:
			return hourRow.getNote();
		case HOURS_INDEX:
			return hourRow.getHours();
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object val, int row, int col) {
		HourRow hourRow = (HourRow) val;
		data.set(row, hourRow);
		fireTableCellUpdated(row, col);
	}
	
	public void removeValueAt(int row) {
		if (row < data.size()) {
			data.remove(row);
			fireTableRowsDeleted(row, row);
		}
	}
	
	/**
	 * Insert an HourRow at the end of the data.
	 * 
	 * @param hourRow the HourRow
	 */
	public void insert(HourRow hourRow) {
		data.add(hourRow);
		this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
	}
}

/**
 * The class InvoicePanel.
 * 
 * Displays controls used for generating invoices
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class InvoicePanel extends TemplatePanel implements ActionListener {
	
	/**
	 * The template name.
	 */
	private static final String TEMPLATE_NAME = "Invoice";
	
	/**
	 * The template source filename.
	 */
	private static final String TEMPLATE_FILENAME = "invoice.tex";
	
	/**
	 * The company label.
	 */
	private JLabel lblCompany;
	
	/**
	 * The address label.
	 */
	private JLabel lblAddress;
	
	/**
	 * The addressee label.
	 */
	private JLabel lblTo;
	
	/**
	 * The addressee address label.
	 */
	private JLabel lblToAddress;
	
	/**
	 * The date label.
	 */
	private JLabel lblDate;
	
	/**
	 * The rate label.
	 */
	private JLabel lblRate;
	
	/**
	 * The entries label.
	 */
	private JLabel lblEntries;
	
	/**
	 * The row note label.
	 */
	private JLabel lblRowNote;
	
	/**
	 * The row hours label.
	 */
	private JLabel lblRowHours;
	
	/**
	 * The company text field.
	 */
	private JTextField txtCompany;
	
	/**
	 * The addressee text field.
	 */
	private JTextField txtTo;
	
	/**
	 * The date text field.
	 */
	private JTextField txtDate;
	
	/**
	 * The rate text field.
	 */
	private JTextField txtRate;
	
	/**
	 * The row note text field.
	 */
	private JTextField txtRowNote;
	
	/**
	 * The row hours text field.
	 */
	private JTextField txtRowHours;
	
	/**
	 * The address text area.
	 */
	private JTextArea txtAddress;
	
	/**
	 * The addressee address text area.
	 */
	private JTextArea txtToAddress;
	
	/**
	 * The add row button.
	 */
	private JButton btnAddRow;
	
	/**
	 * The edit row button.
	 */
	private JButton btnEditRow;
	
	/**
	 * The remove row button.
	 */
	private JButton btnRemoveRow;
	
	/**
	 * The row edit panel.
	 */
	private JPanel rowEditPanel;
	
	/**
	 * The entries table.
	 */
	private JTable tblEntries;
	
	/**
	 * The invoice table model.
	 */
	private InvoiceRowTableModel tableModel;
	
	/**
	 * Instantiate a new InvoicePanel.
	 */
	public InvoicePanel() {
		super(TEMPLATE_NAME, TEMPLATE_FILENAME);
		
		lblCompany = new JLabel("Company");
		lblAddress = new JLabel("Address");
		lblTo = new JLabel("To");
		lblToAddress = new JLabel("To");
		lblDate = new JLabel("Date");
		lblRate = new JLabel("Rate");
		lblEntries = new JLabel("Entries");
		
		inputs.put("company", txtCompany = new JTextField(20));
		inputs.put("address", txtAddress = new JTextArea(5, 20));
		inputs.put("to", txtTo = new JTextField(20));
		inputs.put("to-address", txtToAddress = new JTextArea(5, 20));
		inputs.put("date", txtDate = new JTextField(20));
		inputs.put("rate", txtRate = new JTextField(20));
		inputs.put("entries", tblEntries = new JTable());
		
		tableModel = new InvoiceRowTableModel();
		// tableModel.addTableModelListener(l);
		tblEntries.setModel(tableModel);
		
		lblRowNote = new JLabel("Note");
		lblRowHours = new JLabel("Hours");
		
		txtRowNote = new JTextField(20);
		txtRowHours = new JTextField(20);
		
		(btnAddRow = new JButton("Add")).addActionListener(this);
		(btnEditRow = new JButton("Edit")).addActionListener(this);
		(btnRemoveRow = new JButton("Remove")).addActionListener(this);
		
		rowEditPanel = new JPanel(new MigLayout("wrap 4"));
		rowEditPanel.add(lblRowNote);
		rowEditPanel.add(txtRowNote);
		rowEditPanel.add(lblRowHours);
		rowEditPanel.add(txtRowHours, "wrap");
		rowEditPanel.add(btnAddRow);
		rowEditPanel.add(btnEditRow);
		rowEditPanel.add(btnRemoveRow);
		
		add(lblCompany);
		add(txtCompany);
		add(lblAddress);
		add(new JScrollPane(txtAddress), "wrap");
		add(lblTo);
		add(txtTo);
		add(lblToAddress);
		add(new JScrollPane(txtToAddress), "wrap");
		add(lblDate);
		add(txtDate);
		add(lblRate);
		add(txtRate, "wrap");
		add(lblEntries);
		add(new JScrollPane(tblEntries), "wrap");
		add(rowEditPanel, "dock south");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddRow) {
			if (checkRowInputs()) {
				String note = txtRowNote.getText();
				double hours = Double.parseDouble(txtRowHours.getText());
				HourRow hourRow = new HourRow(note, hours);
				tableModel.insert(hourRow);
			} else {
				JOptionPane.showMessageDialog(this, "Invalid input");
			}
		} else if (e.getSource() == btnEditRow) {
			int selected = tblEntries.getSelectedRow();
			if (selected > -1 && checkRowInputs()) {
				String note = txtRowNote.getText();
				double hours = Double.parseDouble(txtRowHours.getText());
				HourRow hourRow = new HourRow(note, hours);
				tableModel.setValueAt(hourRow, selected, -1);
			}
		} else if (e.getSource() == btnRemoveRow) {
			int selected = tblEntries.getSelectedRow();
			if (selected > -1) {
				tableModel.removeValueAt(selected);
			}
		}
	}
	
	private boolean checkRowInputs() {
		if (txtRowNote.getText().length() <= 0 || txtRowHours.getText().length() <= 0) {
			return false;
		}
		
		try {
			Double.parseDouble(txtRowHours.getText());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
