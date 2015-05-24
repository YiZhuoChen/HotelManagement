package uml.hotel.utils;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private Vector<Vector<Object>> data;
	
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
		fireTableStructureChanged();
	}
	
	public void setData(Vector<Vector<Object>> data) {
		this.data = data;
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		if (columnNames == null) {
			return 0;
		}
        return columnNames.length;
    }

	@Override
    public int getRowCount() {
    	if (data == null) {
			return 0;
		}
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
    	if (columnNames == null) {
    		return null;
		}
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
    	if (data == null) {
			return null;
		}
        return data.get(row).get(col);
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
    	return false;
    }
}
