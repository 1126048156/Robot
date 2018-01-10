package MainFrame.dao;

import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;




/**
 * <code>BubbleModel</code> 
 * 
 * @author Jimmy
 * @since v1.0.0 (Oct 15, 2013)
 */
public class BubbleModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List mLstData;
	
	public BubbleModel() {
		mLstData = new ArrayList();
	}
	
	public void addRow(String sMsg) {
		mLstData.add(sMsg);
		int iLen = mLstData.size();	
	    this.fireTableRowsInserted(iLen - 1, iLen - 1);		
	}	
	public void addRow1(linkbt b) {
		mLstData.add(b);
		int iLen = mLstData.size();	
	    this.fireTableRowsInserted(iLen - 1, iLen - 1);		
	}
	@Override
	public int getRowCount() {
		return mLstData.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		 
		return mLstData.get(rowIndex);
	}	
	@Override
	public boolean isCellEditable(int row,int col) {
		return true;
	}	
	public Class getColumnClass(int c) 
	{
	return getValueAt(0, c).getClass();
	}
}
