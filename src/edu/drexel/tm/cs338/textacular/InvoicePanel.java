package edu.drexel.tm.cs338.textacular;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

class HourRow {
	private String note;
	private double hours;
	
	public HourRow(String note, double hours) {
		this.note = note;
		this.hours = hours;
	}
	
	public String getNote() {
		return note;
	}
	
	public double getHours() {
		return hours;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public void setHours(double hours) {
		this.hours = hours;
	}
}

class InvoiceRowTableModel extends AbstractTableModel {
	
	private static final int NOTE_INDEX = 0;
	private static final int HOURS_INDEX = 1;

	private String[] columnNames = { "Note", "Hours" };
	
	private ArrayList<HourRow> data;
	
	public InvoiceRowTableModel() {
		data = new ArrayList<HourRow>();
		data.add(new HourRow("Foo", 10.0));
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
	
	public void setValueAt(Object val, int row, int col) {
		HourRow hourRow = (HourRow) val;
		data.set(row, hourRow);
		fireTableCellUpdated(row, col);
	}
	
	public void insert(HourRow hourRow) {
		data.add(hourRow);
		fireTableCellUpdated(data.size() - 1, -1);
	}
}

public class InvoicePanel extends TemplatePanel {
	
	private static final String TEMPLATE_NAME = "Invoice";
	private static final String TEMPLATE_FILENAME = "invoice.tex";
	
	private JLabel lblCompany;
	private JLabel lblAddress;
	private JLabel lblTo;
	private JLabel lblToAddress;
	private JLabel lblDate;
	private JLabel lblRate;
	private JLabel lblEntries;
	
	private JTextField txtCompany;
	private JTextField txtTo;
	private JTextField txtDate;
	private JTextField txtRate;
	
	private JTextArea txtAddress;
	private JTextArea txtToAddress;
	
	private JTable tblEntries;
	
	private InvoiceRowTableModel tableModel;
	
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
		tableModel.insert(new HourRow("bar", 12.0));
		
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
	}
}
